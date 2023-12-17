(ns year-2023.day7
  (:require [year-2023.common :refer [get-input]]
            [clojure.string :as s]))


(def test-input
  ["32T3K 765"
   "T55J5 684"
   "KK677 28"
   "KTJJT 220"
   "QQQJA 483"])

(def hand-ranks
  {:five-of-kind 7
   :four-of-kind 6
   :full-house 5
   :three-of-kind 4
   :two-pair 3
   :one-pair 2
   :high-card 1})

;; For part 1 \J should be 11
;; For part 2 \J should be 1
(def card-ranks
  {\A 14
   \K 13
   \Q 12
   \J 1
   \T 10
   \9 9
   \8 8
   \7 7
   \6 6
   \5 5
   \4 4
   \3 3
   \2 2})

(def hand-name
  {[5] :five-of-kind
   [1 4] :four-of-kind
   [2 3] :full-house
   [1 1 3] :three-of-kind
   [1 2 2] :two-pair
   [1 1 1 2] :one-pair
   [1 1 1 1 1] :high-card})

(defn score-hand [cards]
  (let [freqs (sort (vals (frequencies cards)))]
    (hand-name freqs)))

(defn process-line [line score-fn]
  (let [data (clojure.string/split line #" ")
        cards (first data)
        bid (Integer/parseInt (last data))]
    {:cards cards
     :bid bid
     :score (score-fn cards)}))

(defn sort-fn [card1 card2]
  (cond
    (> (hand-ranks (:score card1)) (hand-ranks (:score card2))) 1
    (< (hand-ranks (:score card1)) (hand-ranks (:score card2))) -1
    :else (compare (mapv card-ranks (:cards card1)) (mapv card-ranks (:cards card2)))))

(defn part1 [lines]
  (->> (map #(process-line % score-hand) lines)
       (sort sort-fn)
       (map-indexed (fn [idx item] (* (inc idx) (:bid item))))
       (reduce +)))

#_(part1 (get-input 7))

(defn inc-max [coll]
  (let [max-val (apply max coll)]
    (update coll (.indexOf coll max-val) inc)))

(defn score-hand2 [cards]
  (let [other (or
               (some->> cards
                       (remove #{\J})
                       frequencies
                       vals
                       vec)
               [0])
        jokers (filter #{\J} cards)
        freqs (sort (nth (iterate inc-max other) (count jokers)))]
    (hand-name freqs)))


#_(score-hand2 "JJJJJ")

#_(nth (iterate inc-max [1 2 3]) 1)

#_(score-hand2 "AAJ23")


(->> (get-input 7)
     (map #(process-line % score-hand2))
     (sort sort-fn)
     (map-indexed (fn [idx item] (* (inc idx) (:bid item))))
     (reduce +))
