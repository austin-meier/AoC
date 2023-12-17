(ns year-2023.day1
  (:require [year-2023.common :refer [get-input read-day split-double-lines supermap]]
            [clojure.string :as str]))


(defn part1 []
  (->> (get-input 1)
       (partition-by #(= % ""))
       (filter #(not= % '("")))
       (map (fn [list] (map #(Integer/parseInt %) list)))
       (map #(reduce + %))
       (apply max)))

(part1)


;; Returns a list of total calories each elf is carrying
(defn elf-calories []
    (->> (read-day 1)
       (split-double-lines)
       (map #(str/split % #"\n"))
       (supermap #(Integer/parseInt %))
       (map #(reduce + %))))

;; Find the elf with the most calories
(defn part1-2 []
  (->> (elf-calories)
       (apply max)))

(part1-2)

;; Find the top 3 elves carrying the most calories and sum up the total calories they are carrying
(defn part2 []
  (->> (elf-calories)
       (sort #(compare %2 %1))
       (take 3)
       (reduce +)))

(part2)
