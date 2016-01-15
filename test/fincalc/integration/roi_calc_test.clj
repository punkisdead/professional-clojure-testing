(ns fincalc.integration.roi-calc-test
  (:require [clojure.test :refer :all]
            [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [fincalc.handler :refer [app]]))

(deftest user-can-calculate-roi-on-stock
  (-> (session app)
      (visit "/")
      (has (status? 200) "page exists")
      (within [:h2]
              (has (text? "Enter a Stock Symbol to calculate ROI on...")
                   "Header is there"))
      (fill-in :input.form-control "AAPL")
      (press :button)
      (within [:h2]
              (has (text? "Results") "made it to results page"))
      (within [:#initial]
              (has (some-text? "Stock price one year ago:")))
      (within [:#final]
              (has (some-text? "The latest close was:")))
      (within [:#roi]
              (has (some-text? "Calculated ROI")))))
