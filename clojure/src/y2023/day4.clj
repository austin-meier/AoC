(ns day4
  (:require [year-2023.common :refer [get-input]]
            [com.rpl.specter :as s]
            [clojure.string :as str]))

(defn sublist? [a b]
  (->> a
       (map #(some #{%} b))
       (every? some?)))

(defn part1 []
  (->>
   (get-input 4)
   (s/transform [s/ALL] #(str/split % #","))
   (s/transform [s/ALL s/ALL] #(str/split % #"-"))
   (s/transform [s/ALL s/ALL] (fn [[lower upper]] (range (parse-long lower) (+ 1 (parse-long upper)))))
   (filter (fn [[a b]] (or
                        (sublist? a b)
                        (sublist? b a))))
   (count)))

(part1)

(defn overlaps? [a b]
  (->> a
       (map #(some #{%} b))
       any?))

(defn part2 []
  (->>
   (get-input 4)
   (s/transform [s/ALL] #(str/split % #","))
   (s/transform [s/ALL s/ALL] #(str/split % #"-"))
   (s/transform [s/ALL s/ALL] (fn [[lower upper]] (range (parse-long lower) (parse-long upper))))
   (filter (fn [[a b]] (overlaps? a b)))
   (count)))

(part2)

(-> (s/insert-into :stockdatatable)
    (s/columns :symbol :date :close)
    (s/values
     [["IBM" "Smith" 34]]))


(def data
  {"2023-04-01 15:00:00" {:close 27}})
