(ns fincalc.db-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as sql]
            [fincalc.db :refer :all]))

(declare ^:dynamic *txn*)

(def db "postgresql://localhost:5432/fincalc")

(use-fixtures :each
  (fn [f]
    (sql/with-db-transaction
      [transaction db]
      (sql/db-set-rollback-only! transaction)
      (binding [*txn* transaction] (f)))))

(deftest retrieve-all-stocks
  (testing
    (is (some #{"AAPL"} (get-symbols *txn*)))
    (is (some #{"YHOO"} (get-symbols *txn*)))))

(deftest insert-new-symbol
  (testing
    (add-symbol *txn* "NOK")
    (is (some #{"NOK"} (get-symbols *txn*)))))

(deftest inserted-symbol-rolled-back
  (testing
    (is (not (some #{"NOK"} (get-symbols *txn*))))))
