(ns fincalc.handler
  (:require [fincalc.api :as api]
            [fincalc.views.layout :as layout]
            [fincalc.views.contents :as contents]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [ring.adapter.jetty :as jetty]))

(defonce server (atom nil))

(defroutes api-routes
  (GET "/api/stocks" [] (api/all-stocks))
  (POST "/api/stocks" [sym] (api/create-stock sym))
  (DELETE "/api/stocks/:sym" [sym] (api/remove-stock sym))
  (GET "/api/roi/:sym" [sym] (api/calculate sym)))

(defroutes app-routes
  (GET "/" [] (layout/application "Home" (contents/index)))
  (POST "/" [sym] (layout/application "Results" (contents/calculate sym))))

(defroutes not-found
  (ANY "*" [] (route/not-found (layout/application "Page Not Found" (contents/not-found)))))

(def app
  (-> (routes
       api-routes
       (wrap-defaults app-routes site-defaults)
       not-found)
      (wrap-defaults api-defaults)))

(defn start-server [& [port]]
  (let [port (if port port 8080)]
    (reset! server
            (jetty/run-jetty app {:port port :join? false}))
    (println (str "You can view the site at http://localhost:" port))))

(defn stop-server []
  (.stop @server)
  (reset! server nil))
