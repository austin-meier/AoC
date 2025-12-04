(ns year-2017.day2
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [com.rpl.specter :as s]))

(def input "resources/day2/input.txt")

(defn get-nums []
  (-> (slurp input)
      (str/replace #"\n" "\t")
      (str/split #"\t")
      (->>
       (map #(Integer/parseInt %)))))

(defn get-rows []
  (-> (slurp input)
      (str/split #"\n")))

(defn to-digits [row]
  (-> row
      (str/split #"\t")
      (->> (map #(Integer/parseInt %)))))

(defn get-difference [row]
  (let [min (apply min row)
        max (apply max row)]
    (- max min)))

(defn part-1
  "Day 2 Part 1"
  []
  (->> (get-rows)
       (map to-digits)
       (map get-difference)
       (reduce +)))

(part-1)

(defn evenly-divisible? [[a b]]
  (if (zero? b)
    0
    (zero? (mod (max a b) (min a b)))))

(defn divide [[a b]]
  (/ (max a b) (min a b)))

#_(->> [[5 9 2 8]
      [9 4 7 3]
      [3 8 6 5]]
     (s/transform [s/ALL] #(combo/combinations % 2))
     (s/select [s/ALL s/ALL evenly-divisible?])
     (transduce (map divide) +))

(defn part2 []
  (->> input
       slurp
       (s/transform [] #(str/split % #"\n" ))
       (s/transform [s/ALL] #(str/split % #"\t"))
       (s/transform [s/ALL s/ALL] parse-long)
       (s/transform [s/ALL] #(combo/combinations % 2))
       (s/select [s/ALL s/ALL evenly-divisible?])
       (transduce (map divide) +)))









(to-digits 255 16)

(defn to-digits
  "Convert a positive number n to digits in base"
  ([n base] (to-digits n base (list)))
  ([n base digits]
   (if (> n 0)
     (recur (quot n base) base (conj digits (mod n base)))
     digits)))

(from-digits [1 1] 2)

(defn from-digits
  ([digits base]
   (reduce (fn [acc digit] (+ digit (* acc base))) 0 digits)))

(defn convert-base
  "Convert digits collection from base b1 to base b2"
  [digits b1 b2]
  (to-digits (from-digits digits b1) b2))

(def alphanumeric-map
  {0 \0, 1 \1, 2 \2, 3 \3, 4 \4, 5 \5, 6 \6, 7 \7, 8 \8, 9 \9,
   10 \a, 11 \b, 12 \c, 13 \d, 14 \e, 15 \f, 16 \g, 17 \h,
   18 \i, 19 \j, 20 \k, 21 \l, 22 \m, 23 \n, 24 \o, 25 \p,
   26 \q, 27 \r, 28 \s, 29 \t, 30 \u, 31 \v, 32 \w, 33 \x,
   34 \y, 35 \z})

(defn stringify [digits]
  (apply str (map alphanumeric-map digits)))

(defn numberify [s]
  (map (set/map-invert alphanumeric-map) s))


(defn giga-convert [n b1 b2]
  (stringify (convert-base (numberify n) b1 b2)))

(giga-convert "10" 10 2)
