(ns fincalc.core-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as t]
            [vcr-clj.clj-http :refer [with-cassette]]
            [fincalc.core :refer :all]))

(deftest date-calculations
  (testing "1 year ago"
    (is (= (t/minus (t/today) (t/years 1)) (one-year-ago)))))

(deftest date-calculations-with-redefs
  (with-redefs [t/today (fn [] (t/local-date 2016 1 10))]
    (testing "1 year  ago"
      (are [exp actual] (= exp actual)
        (t/local-date 2015 1 10) (one-year-ago)
        (t/local-date 2015 1 1) (one-year-ago (t/local-date 2016 1 1))))
    (testing "yesterday"
      (is (= (t/local-date 2016 1 9) (yesterday))))))

(deftest calculate-roi
  (testing
      (are [exp actual] (= exp actual)
          30.0 (roi 5000 6500)
          0.0 (roi 5000 5000)
          -20.0 (roi 5000 4000))))

(deftest building-yql-query
  (testing
      (is (= "select * from yahoo.finance.historicaldata where symbol = \"AAPL\" and startDate = \"2016-01-01\" and endDate = \"2016-01-01\"" (build-yql "AAPL" (t/local-date 2016 1 1))))))

(deftest vcr-tests
  (with-cassette :stocks
    (is (= 97.129997 (get-close-for-symbol "AAPL" (t/local-date 2016 1 17))))
    (is (= 29.139999 (get-close-for-symbol "YHOO" (t/local-date 2016 1 17))))))
