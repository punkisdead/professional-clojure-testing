(ns fincalc.api
  (:require [fincalc.core :refer :all]
            [fincalc.db :refer :all]
            [cheshire.core :as json]))

(defn all-stocks []
  (let [stocks (get-symbols db-spec)]
    (json/generate-string stocks)))

(defn create-stock [sym]
  (if (empty? (get-symbol db-spec sym))
    (add-symbol db-spec sym))
  {:status 201})

(defn remove-stock [sym]
  (remove-symbol db-spec sym)
  {:status 204})

(defn calculate [sym]
  (let* [final (get-close-for-symbol sym (today))
         initial (get-close-for-symbol sym (one-year-ago))
         result (roi initial final)]
    (json/generate-string {:initial initial :final final :roi result})))
