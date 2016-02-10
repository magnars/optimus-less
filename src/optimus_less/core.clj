(ns optimus-less.core
  (:require [clojure.string :as str]
            [optimus.assets.load-css :refer [create-css-asset]]
            [optimus.assets.creation :refer [last-modified existing-resource]])
  (:import [com.github.sommeri.less4j.core DefaultLessCompiler ]
           com.github.sommeri.less4j_javascript.Less4jJavascript))

(defn compile-less [less]
  (let [config (com.github.sommeri.less4j.LessCompiler$Configuration.)]
    (Less4jJavascript/configure config)
    (-> (DefaultLessCompiler.)
      (.compile less config)
      (.getCss))))

(defn load-less-asset [public-dir path]
  (let [resource (existing-resource public-dir path)]
    (-> (create-css-asset (str/replace path #"\.less$" ".css")
                          (compile-less resource)
                          (last-modified resource))
        (assoc :original-path path))))

(defmethod optimus.assets.creation/load-asset "less"
  [public-dir path]
  (load-less-asset public-dir path))
