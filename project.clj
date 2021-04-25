(defproject optimus-less "0.2.1"
  :description "LESS asset loader for Optimus."
  :url "http://github.com/magnars/optimus-less"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.github.sommeri/less4j "1.8.5"]
                 [com.github.sommeri/less4j-javascript "0.0.1"]
                 [optimus "0.17.1"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.1"]
                                  [midje "1.9.9"]
                                  [test-with-files "0.1.1"]]
                   :plugins [[lein-midje "3.2.1"]]
                   :resource-paths ["test/resources"]}})
