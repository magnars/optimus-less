(ns optimus-less.core
  (:require [clojure.string :as str]
            [optimus.assets.load-css :refer [create-css-asset]]
            [optimus.assets.creation :refer [last-modified existing-resource]])
  (:import [com.github.sommeri.less4j.core DefaultLessCompiler]))

(defn compile-less [less]
  (-> (DefaultLessCompiler.)
      (.compile less)
      (.getCss)))

(defn load-less-asset [public-dir path]
  (let [resource (existing-resource public-dir path)]
    (-> (create-css-asset (str/replace path #"\.less$" ".css")
                          (compile-less resource)
                          (last-modified resource))
        (assoc :original-path path))))

(defmethod optimus.assets.creation/load-asset "less"
  [public-dir path]
  (load-less-asset public-dir path))
