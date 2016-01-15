(ns fincalc.views.contents
  (:require [fincalc.core :refer :all]
            [hiccup.form :as form]
            [hiccup.element :refer (link-to)]
            [ring.util.anti-forgery :as anti-forgery]))

(defn index []
  [:div {:id "content"}
   [:h2 "Enter a Stock Symbol to calculate ROI on..."]
   (form/form-to [:post "/"]
                 (anti-forgery/anti-forgery-field)
                 [:div {:class "col-lg-6"}
                  [:div {:class "input-group"}
                   [:input {:type "text" :name "sym" :class "form-control" :placeholder "Enter Stock Symbol..."}]
                   [:span {:class "input-group-btn"}
                    [:button {:class "btn btn-default" :type "submit"} "Calculate"]]]])])

(defn calculate [sym]
  (let* [final (get-close-for-symbol sym (today))
         initial (get-close-for-symbol sym (one-year-ago))
         result (roi initial final)]
    [:div {:id "content"}
     [:h2 "Results"]
     [:p {:id "final"} (str "The latest close was: $" (format "%.2f" final))]
     [:p {:id "initial"} (str "Stock price one year ago: $" (format "%.2f" initial))]
     [:p {:id "roi"} (str "Calculated ROI: " (format "%.2f" result) "%")]
     (link-to {:class "btn btn-primary" :id "go-home"} "/" "Go Home")]))

(defn not-found []
  [:div
   [:h1 {:class "info-warning"} "Page Not Found"]
   [:p "This is not the page you were looking for."]
   (link-to {:class "btn btn-primary" :id "go-home"} "/" "Take me to Home")])
