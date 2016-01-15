(defproject fincalc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [com.cemerick/url "0.1.1"]
                 [clj-time "0.11.0"]
                 [cheshire "5.5.0"]
                 [clj-http "2.0.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]]
  :plugins [[lein-ring "0.9.7"]
            [org.clojars.punkisdead/lein-cucumber "1.0.5"]]
  :cucumber-feature-paths ["features"]
  :ring {:handler fincalc.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [com.gfredericks/vcr-clj "0.4.6"]
                        [clj-webdriver/clj-webdriver "0.7.2"]
                        [org.seleniumhq.selenium/selenium-server "2.48.2"]
                        [kerodon "0.7.0"]]}})
