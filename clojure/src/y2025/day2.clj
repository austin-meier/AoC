
(ns y2025.day2
  (:require [common :refer [get-input-lines get-input]]
            [clojure.string :as str]))

(def test-input
  "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
1698522-1698528,446443-446449,38593856-38593862,565653-565659,
824824821-824824827,2121212118-2121212124")

(def input (get-input 2025 2))

(defn parse-input [input]
  (-> input
      (str/split #",")
      (->> (map str/trim)
           (map (fn [s]
                  (let [[lower upper] (map parse-long (str/split s #"-"))]
                    (range lower (inc upper))))))))
(defn invalid1? [n]
  (let [[first second] (split-at (quot (count (str n)) 2) (str n))]
    (= first second)))

(defn invalid2? [n]
  (let [strn (str n)
        len (count strn)]
    (->> (range 1 (inc (quot len 2)))
         (map #(subs strn 0 %))
         (filter #(= strn (apply str (repeat (/ len (count %)) %))))
         (seq))))


(defn run [invalid-fn]
  (->>
   (parse-input input)
   (map #(reduce + (filter invalid-fn %)))
   (reduce +)))

;; Part 1
(run invalid1?)
;; Part 2
(run invalid2?)
