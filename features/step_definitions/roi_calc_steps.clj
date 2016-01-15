(require '[clj-webdriver.taxi :as taxi]
         '[fincalc.browser :refer [browser-up browser-down]]
         '[clojure.test :refer :all])

(Before ["@ui"]
        (browser-up))

(After ["@ui"]
       (browser-down))

(Given #"^I am at the \"homepage\"$" []
       (taxi/to "http://localhost:3000/"))

(Given #"^I am at an invalid page$" []
       (taxi/to "http://localhost:3000/invalid"))

(When #"^I click the title bar$" []
      (taxi/click "a#brand-link"))

(Then #"^I should be at the \"homepage\"$" []
      (assert (= (taxi/title) "Home")))

(When #"^I calculate the ROI for the symbol \"(.*?)\"$" [sym]
      (taxi/input-text "input[name=\"sym\"]" sym)
      (taxi/click "button[type=\"submit\"]"))

(Then #"^I should see an initial value$" []
      (taxi/wait-until #(= (taxi/title) "Results"))
      (assert (re-find #"Stock price one year ago:" (taxi/text "#initial"))))

(Then #"^I should see a final value$" []
      (assert (re-find #"The latest close was:" (taxi/text "#final"))))

(Then #"^I should see an ROI$" []
      (assert (re-find #"Calculated ROI" (taxi/text "#roi"))))

(Then #"^I should see a message stating \"(.*?)\"$" [arg1]
      (assert (= "Page Not Found" (taxi/text "h1.info-warning"))))

(When #"^I click on the \"Home\" button$" []
      (taxi/click "#go-home"))
