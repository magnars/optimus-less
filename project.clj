(defproject optimus-less "0.2.0"
  :description "LESS asset loader for Optimus."
  :url "http://github.com/magnars/optimus-less"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.github.sommeri/less4j "1.8.0"]
                 [optimus "0.15.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]
                                  [test-with-files "0.1.0"]]
                   :plugins [[lein-midje "3.1.3"]]
                   :resource-paths ["test/resources"]}})
