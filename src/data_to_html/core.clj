(ns data-to-html.core
  (require [data-to-html.css :refer [styles]])
  (require [hiccup.core :as h])
  (require [hiccup.page :as p])
  (:gen-class))

(import 'java.awt.Desktop)
(import 'java.net.URI)
(import 'java.lang.System)

(def visit) ; pre-define symbol

(defn- visit-vector [index element]
  (list [:div.index (str index)]
        (visit element)))

(defn- visit-map-pair [[k v]]
  (list [:div.key (if (keyword? k) (name k) (str k))]
        (visit v)))

(defn- visit [node]
  (cond (or (vector? node) (list? node)) [:div.vector (map visit-vector (range) node)]
        (set? node) [:div.set (map visit node)]
        (map? node) [:div.map (map visit-map-pair node)]
        (string? node) [:div.string (str \" node \")]
        (keyword? node) [:div.keyword (str node)]
        (nil? node) [:div.type "nil"]
        :else [:div.type (str node)]))

(defn convert [data]
  "Converts a clojure data structure to a html string with nested divs and some css."
  (h/html
   (p/html5
    [:head [:style styles]]
    [:body (visit data)])))

(defn- pwd []
  (System/getProperty "user.dir"))

(defn open-in-browser
  "Creates a file and opens it with a browser for your pleasurable viewing."
  ([html]
    (open-in-browser html "output.html"))
  ([html filename]
    (spit filename html)
    (doto (Desktop/getDesktop)
      (.browse (URI. (str "file://" (pwd) "/" filename))))))
