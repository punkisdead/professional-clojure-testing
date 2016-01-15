(ns fincalc.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [fincalc.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (re-find #"Enter a Stock Symbol" (:body response)))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
