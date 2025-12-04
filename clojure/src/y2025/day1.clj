(ns y2025.day1
  (:require [common :refer [get-input-lines get-input]]
            [clojure.string :as str]))

(def test-input
  ["L68"
   "L30"
   "R48"
   "L5"
   "R60"
   "L55"
   "L1"
   "L99"
   "R14"
   "L82"])

(def input (get-input-lines 2025 1))

(defn degrees [s]
  (case (first s)
    \L  (- (parse-long (subs s 1)))
    \R  (parse-long (subs s 1))))

(->> input
 (reduce
  (fn [acc x]
    (let [deg (degrees x)
          current (:deg acc)
          new-deg (+ current deg)
          crossings (if (pos? deg)
                      (- (Math/floor (/ new-deg 100.0))
                         (Math/floor (/ current 100.0)))
                      (- (Math/floor (/ (dec current) 100.0))
                         (Math/floor (/ (dec new-deg) 100.0))))
          normalized (rem new-deg 100)]
      {:deg normalized
       :end (if (zero? normalized)
              (inc (:end acc))
              (:end acc))
       :during (+ (:during acc) crossings)}))
  {:deg 50 :end 0 :during 0}))