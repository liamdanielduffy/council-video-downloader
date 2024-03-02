(ns env
  (:require [clojure.string :as string]))

(defn load-env-file []
  (try
    (let [lines (slurp ".env")
          env-map (reduce (fn [acc line]
                            (let [[k v] (clojure.string/split line #"\=")]
                              (assoc acc k v)))
                          {}
                          (clojure.string/split-lines lines))]
      env-map)
    (catch Exception _
      {})))


(defn get-env []
  (merge (load-env-file) (System/getenv)))
