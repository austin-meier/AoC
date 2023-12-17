(ns year-2023.common
  (:require [clojure.string :as str]))

(def resources-folder "./resources/")

(defn read-day [day]
  (->> (str resources-folder day ".txt")
       (slurp)
       (str/trim)))


(defn get-input [day]
  (->> (str resources-folder day ".txt")
       (slurp)
       (str/trim)
       (#(str/split % #"\n"))))

(defn split-double-lines [s]
  (str/split s #"\n\n"))

(defn supermap [f m]
  (map #(f %) m))
