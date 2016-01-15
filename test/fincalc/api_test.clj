(ns fincalc.api-test
  (:require [fincalc.handler :refer [app]]
            [fincalc.api :refer :all]
            [cheshire.core :as json]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))

(deftest get-all-stocks
  (testing
      (is (some #{"AAPL"} (json/parse-string (all-stocks))))))

(deftest get-all-stocks-ring-mock
  (let [response (app (mock/request :get "/api/stocks"))]
    (is (= (:status response) 200))
    (is (some #{"AAPL"} (json/parse-string (:body response))))))
