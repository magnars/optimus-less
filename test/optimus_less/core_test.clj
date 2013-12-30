(ns optimus-less.core-test
  (:require [optimus-less.test-helper :refer [with-files public-dir *last-modified*]]
            [clojure.java.io :as io])
  (:use [optimus-less.core]
        [midje.sweet]))

(def source-map-fluff "/*# sourceMappingURL=main.css.map */\n")

(fact
 "It compiles strings."

 (compile-less "* { margin: 1 + 1; }") => "* {\n  margin: 2;\n}\n")

(fact
 "It compiles resources."

 (with-files [["/main.less" "* { margin: 1 + 1; }"]]
   (compile-less (io/resource (str public-dir "/main.less"))) => (str "* {\n  margin: 2;\n}\n" source-map-fluff)))

(fact
 "It finds imports."

 (with-files [["/library.less" "body { background: red; }"]
              ["/main.less" "@import \"library.less\"; h1 { background: blue; }"]]
   (compile-less (io/resource (str public-dir "/main.less"))) => (str "body {\n  background: red;\n}\nh1 {\n  background: blue;\n}\n" source-map-fluff)))

(fact
 "It creates css assets."

 (with-files [["/main.less" "* { margin: 1 + 1; }"]]
   (load-less-asset public-dir "/main.less") => {:path "/main.css"
                                                 :original-path "/main.less"
                                                 :contents (str "* {\n  margin: 2;\n}\n" source-map-fluff)
                                                 :last-modified *last-modified*
                                                 :references #{}}))

(fact
 "It includes references, rewriting them to absolute paths."

 (with-files [["/theme/main.less" "body { background: url(img/bg.png); }"]
              ["/theme/img/bg.png" ""]]
   (load-less-asset public-dir "/theme/main.less") => {:path "/theme/main.css"
                                                       :original-path "/theme/main.less"
                                                       :contents (str "body {\n  background: url('/theme/img/bg.png');\n}\n" source-map-fluff)
                                                       :last-modified *last-modified*
                                                       :references #{"/theme/img/bg.png"}}))

(fact
 "It straps into Optimus' load-asset."

 (with-files [["/main.less" "* { margin: 1 + 1; }"]]
   (-> (optimus.assets.creation/load-asset public-dir "/main.less") :path) => "/main.css"))
