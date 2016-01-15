(ns fincalc.core
  (:require [clj-time.core :as t]
            [clj-time.format :as f]
            [cemerick.url :refer (url)]
            [cheshire.core :as json]
            [clj-http.client :as client]))

(defn today []
  (t/today))

(defn yesterday []
  (t/minus (today) (t/days 1)))

(defn one-year-ago
  ([] (one-year-ago (today)))
  ([date] (t/minus date (t/years 1))))

(defn build-yql [sym date]
  (let [formatted-date (f/unparse-local-date (f/formatters :year-month-day) date)]
    (str "select * from yahoo.finance.historicaldata where symbol = \"" sym  "\" and startDate = \"" formatted-date "\" and endDate = \"" formatted-date "\"")))

(defn build-get-url [sym date]
  (-> (url "https://query.yahooapis.com/v1/public/yql")
      (assoc :query {:q (build-yql sym date)
                     :format "json"
                     :env "store://datatables.org/alltableswithkeys"})
      str))

(defn get-close-for-symbol [sym date]
  (loop [close-date date retries 5]
    (let [result (get-in
                   (json/parse-string
                    (:body (client/get (build-get-url sym close-date))))
                   ["query" "results" "quote" "Adj_Close"])]
      (if (and (nil? result) (> retries 0))
        (recur (t/minus close-date (t/days 1)) (dec retries))
        (read-string result)))))

(defn roi [initial earnings]
  (double (* 100 (/ (- earnings initial) initial))))
