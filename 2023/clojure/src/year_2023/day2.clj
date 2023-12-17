(ns year-2023.day2
  (:require [year-2023.common :refer [get-input]]
            [clojure.string :as str]
            [criterium.core :as c]))

(def decrypt
  {\A :rock
   \B :paper
   \C :scissors
   \X :rock
   \Y :paper
   \Z :scissors})

(def score
  {:win 6
   :draw 3
   :lose 0
   :rock 1
   :paper 2
   :scissors 3})

(defn get-moves [match]
  (->> (map decrypt match)
       (filter some?)))

(def match-result
  {[:rock :rock] [:rock :draw]
   [:rock :paper] [:paper :win]
   [:rock :scissors] [:scissors :lose]
   [:paper :rock] [:rock :lose]
   [:paper :paper] [:paper :draw]
   [:paper :scissors] [:scissors :win]
   [:scissors :rock] [:rock :win]
   [:scissors :paper] [:paper :lose]
   [:scissors :scissors] [:scissors :draw]})

(defn score-match [match]
  (->> (map score match)
       (reduce +)))


(defn match-score [[my-move match-result]]
  (+ (score my-move) (score match-result)))

(match-score [:rock :win])

(->> (get-input 2)
     (map get-moves)
     (map match-result)
     (map score-match)
     (reduce +))

(defn part1 []
  (->> (get-input 2)
       (map get-moves)
       (map win?)
       (map score-match)
       (reduce +)))

(part1)

;; Part 2
(def strategy
  {\A :rock
   \B :paper
   \C :scissors
   \X :lose
   \Y :draw
   \Z :win})

(defn get-strategy [match]
  (->> (map strategy match)
       (filter some?)))

(->> (get-input 2)
     (map get-strategy))
