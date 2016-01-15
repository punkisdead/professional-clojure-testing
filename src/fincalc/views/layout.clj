(ns fincalc.views.layout
  (:use [hiccup.page :only (html5 include-css include-js)]))

(defn application [title & content]
  (html5 [:head
          [:title title]
          (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
          (include-css "/css/starter-template.css")
          (include-js "https://code.jquery.com/jquery-2.2.0.min.js")
          (include-js "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js")

          [:body
           [:nav {:class "navbar navbar-inverse navbar-fixed-top"}
            [:div {:class "container"}
             [:a {:class "navbar-brand" :href "/" :id "brand-link"} "ROI Calculator"]]]
           [:div {:class "container"} content ]]]))
