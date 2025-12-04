(ns 2023.day3
  (:require [com.rpl.specter :as s]
            [year-2023.common :refer [get-input]]))

;; in ASCI 'a' = 97 and 'A' = 65
;; if n > 96 then it's a lowercase character and we can subtract 96 to get the priority 1-26
;; otherwise we can subtract 65 - 27 since we want A to start with priority 27

(defn get-priority [n]
  (let [i (int n)]
    (if (> i 96)
      (- i 96)
      (- i (- 65 27)))))

(defn part1 []
  (->> ;; get input for day
       (get-input 3)
       ;; split in half
       (map #(split-at (/ (count %) 2) %))
       (map (fn [[a b]]
              (filter #(.contains b %) a)))
       (map #(get-priority (first %)))
       (reduce +)))

(part1)

(defn part2 []
  (->> (get-input 3)
       (partition 3)
       (map (fn [[a b c]]
              (filter #(and (.contains b (str %)) (.contains c (str %))) a)))
       (map #(get-priority (first %)))
       (reduce +)))

(part2)
