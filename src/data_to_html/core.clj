(ns data-to-html.core
  (require [hiccup.core :as h])
  (require [hiccup.page :as p]))

(def visit) ; pre-define symbol

(defn visit-vector [index element]
  (list [:div.index (str index)]
        (visit element)))

(defn visit-map-pair [[k v]]
  (list [:div.key (if (keyword? k) (name k) (str k))]
        (visit v)))

(defn visit [node]
  (cond (or (vector? node) (list? node)) [:div.vector (map visit-vector (range) node)]
        (set? node) [:div.set (map visit node)]
        (map? node) [:div.map (map visit-map-pair node)]
        (string? node) [:div.string (str \" node \")]
        (keyword? node) [:div.keyword (str node)]
        (nil? node) [:div.type "nil"]
        :else [:div.type (str node)]))

(defn convert [input]
  "Converts a clojure data structure to a html page of nested divs"
  (h/html
   (p/include-css "style.css")
   (p/html5 [:body (visit input)])))
