(require '[clojure.test :refer :all]
         '[clj-http.client :as client]
         '[cheshire.core :as json]
         '[fincalc.db :as db])

(def response (atom nil))
(def base-url "http://localhost:3000")

(When #"^I send a GET request to \"(.*?)\"$" [path]
      (let [endpoint (str base-url path)]
        (reset! response (client/get endpoint))))

(When #"^I send a POST request to \"(.*?)\" with the following params:$" [path req-params]
      (let [endpoint (str base-url path)
            form-params (kv-table->map req-params)]
        (reset! response (client/post endpoint {:form-params form-params}))))

(When #"^I send a DELETE request to \"(.*?)\"$" [path]
      (let [endpoint (str base-url path)]
        (reset! response (client/delete endpoint))))

(Then #"^the response status should be \"(.*?)\"$" [status-code]
      (assert (= status-code (str (:status @response)))))

(Then #"^I should see the following JSON in the body:$" [expected-body]
      (assert (= (json/parse-string expected-body)
             (json/parse-string (:body @response)))))

(Then #"^the response body should be empty$" []
      (assert (empty? (:body @response))))

(Given #"^a stock with symbol \"(.*?)\" exists in the database$" [sym]
       (assert (not (empty? (db/get-symbol db/db-spec sym)))))

(Then #"^a stock with symbol \"(.*?)\" does not exist in the database$" [sym]
      (assert (empty? (db/get-symbol db/db-spec sym))))
