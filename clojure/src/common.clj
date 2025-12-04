(ns common
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn file-exists? [path]
  (.exists (io/file path)))

(defn call-bash-script [year day]
  (->
    (ProcessBuilder. ["bash" "get-input.sh" (str year) (str day)])
    (.directory (-> (io/file *file*) .getParentFile .getParentFile .getParentFile))
    (.start)
    (.waitFor)))

(defn get-input [year day]
  (let [path (format "../inputs/%d/day%d.txt" year day)]
    (when-not (file-exists? path)
      (call-bash-script year day))
    (slurp path)))

(defn get-input-lines [year day]
  (let [input (get-input year day)]
    (str/split-lines input)))