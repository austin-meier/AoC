(ns year-2017.day1
  (:require [clojure.string :as str]))


(def input "resources/day1/input.txt")

#_(->> (slurp input)
     (partition 2 2)
     (filter (fn [[a b]] (= a b)))
     (reduce #(+ %1 (Integer/parseInt %2)) 0))

(defn get-digits []
  (-> (slurp input)
      (str/split #"")
      (->>
       (map #(Integer/parseInt %)))))


#_(map #(+ %1 %2) r (next r))

(defn part-1
  "Day 1 part 1 solution"
  []
  (let [i (get-digits)
        processed (reduce + (map #(if (= %1 %2) %1 0) i (conj (vec (next i)) (first i))))]
    processed))
#_(->> (get-digits)
     ((fn [x] (partition 2 1 x x)))
     (filter (fn [[a b]] (= a b)))
     (map first)
     (reduce +))

#_(part-1)

;; (let [i (get-digits)
;;       l (reduce + (map #(if (= %1 %2) %1 0) i (next i)))
;;       fh (split-at (/ 2 (count l)) l)]
;;   fh)

(defn part-2
  "Day 1 part 2 solution"
  []
  (let [i     (get-digits)
        half  (/ (count i) 2)
        fh    (take half i)
        sh    (drop half i)
        l     (map #(if (= %1 %2) (* 2 %1) 0) fh sh)]
    (reduce + l)))

(as-> (get-digits) $
  (split-at (/ (count $) 2) $)
  )

(defn split-half [c]
  (let [half-index (/ (count c) 2)]
    [(take half-index c) (drop half-index c)]))

#_(->> (get-digits)
     (split-half)
     (equal-vals))


#_(part-2)
