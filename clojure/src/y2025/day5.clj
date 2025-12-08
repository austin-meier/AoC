(ns y2025.day5
  (:require [common :refer [get-input-lines get-input]]
            [clojure.string :as str]))

(def test-input
  ["3-5"
   "10-14"
   "16-20"
   "12-18"
   ""
   "1"
   "5"
   "8"
   "11"
   "17"
   "32"])

(def input (get-input-lines 2025 5))

(defn parse-input [input]
  (->> input
       (partition-by str/blank?)
       (#(vector (first %) (last %)))))

(defn parse-range [s]
  (map parse-long (str/split s #"-")))

(defn part1 [input]
  (let [[range-strs nums] (parse-input input)
        ranges (map parse-range range-strs)]
    (-> (filter
         (fn [n]
           (some (fn [r]
                     (let [[lower upper] r]
                       (and (<= lower n) (>= upper n))))
                 ranges))
         (map parse-long nums))
        (count))))

(defn part2 [input]
  (let [[range-strs _] (parse-input input)
        ranges (->> range-strs
                    (map parse-range)
                    (sort-by first))]
    (->> ranges
         (reduce
          (fn [acc [lower upper]]
            (if (empty? acc)
              [[lower upper]]
              (let [[prev-lower prev-upper] (peek acc)]
                (if (<= lower prev-upper)
                  (conj (pop acc) [prev-lower (max prev-upper upper)])
                  (conj acc [lower upper])))))
          [])
         (map (fn [[lower upper]] (- (inc upper) lower)))
         (reduce +))))
