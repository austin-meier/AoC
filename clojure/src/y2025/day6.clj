(ns y2025.day6
  (:require [common :refer [get-input-lines get-input]]
            [clojure.string :as str]))

(def test-input
  ["123 328  51 64"
   "45 64  387 23"
   "6 98  215 314"
   "*   +   *   +  "])

(def input (get-input-lines 2025 6))

(defn run
  [input]
  (let [operands
        (map #(->> %
                   (re-seq #"\d+")
                   (map parse-long))
             (butlast input))
        operators (map (comp resolve symbol)
                       (re-seq #"\S" (last input)))]
    (->>
     (map-indexed
      (fn [idx op]
        (apply op (map #(nth % idx) operands)))
      operators)
     (reduce +))))

(run input)

