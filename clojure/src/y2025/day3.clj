(ns y2025.day3
  (:require [common :refer [get-input-lines get-input]]
            [clojure.string :as str]))


(def test-input
  ["987654321111111"
   "811111111111119"
   "234234234234278"
   "818181911112111"])

(def input (get-input-lines 2025 3))

(defn char->int
  [c]
  (- (int c) (int \0)))

(defn largest-idx
  [coll]
  (.indexOf coll (apply max coll)))

(defn coll->num [coll]
  (reduce (fn [acc x] (+ (* acc 10) x)) 0 coll))

(defn bank-joltage
  [bank digits]
  (->>
   (range digits)
   (reduce
    (fn [acc pad]
      (let [offset (:offset acc)
            window (subvec bank offset (- (count bank) (- digits pad 1)))
            idx (largest-idx window)]
        {:offset (+ offset (inc idx)) :joltage (conj (:joltage acc) (get window idx))}))
    {:offset 0 :joltage []})
   (:joltage)
   (coll->num)))

(defn run [window]
  (->>
   input
   (map #(mapv char->int %))
   (map #(bank-joltage % window))
   (reduce +)))

(comment
  ;; Part 1
  (run 2)
  ;; Part 2
  (run 12)
  )