(ns y2025.day4
  (:require [common :refer [get-input-lines get-input]]
            [clojure.string :as str]))


(def test-input
  ["..@@.@@@@."
   "@@@.@.@.@@"
   "@@@@@.@.@@"
   "@.@@@@..@."
   "@@.@@@@.@@"
   ".@@@@@@@.@"
   ".@.@.@.@@@"
   "@.@@@.@@@@"
   ".@@@@@@@@."
   "@.@.@@@.@."])

(def input (get-input-lines 2025 4))

input

(defn neighbors [coll x y]
  (->> [[-1 -1] [0 -1] [1 -1]
        [-1  0]        [1  0]
        [-1  1] [0  1] [1  1]]
       (map (fn [[dx dy]] [(+ x dx) (+ y dy)]))
       (filter (fn [[nx ny]]
                 (and (>= nx 0) (< nx (count (first coll)))
                      (>= ny 0) (< ny (count coll)))))
       (map (fn [[nx ny]] (get-in coll [ny nx])))))

(defn part1 [input]
(->>
 input
 (keep-indexed
  (fn [y row]
    (let [n (keep-indexed
             (fn [x val]
               (when (= val \@)
                 (filter #{\@} (neighbors input x y))))
             row)]
      (when (not-empty n) (keep #(when (< (count %) 4) 1) n)))))
 (flatten)
 (reduce +)))

