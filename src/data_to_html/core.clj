(ns data-to-html.core
  (require [data-to-html.css :as css]
           [hiccup.core :as h]
           [hiccup.page :as p])
  (import [java.awt.Desktop]
          [java.net.URI]
          [java.lang.System])
  (:gen-class))

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

(defn convert
  "Converts a clojure data structure to a html string with nested divs and some css."
  [data]
  (h/html
   (p/html5
    [:head [:style (css/get-css)]]
    [:body (visit data)])))

(defn- pwd []
  (java.lang.System/getProperty "user.dir"))

(defn open-in-browser
  "Creates a file and opens it with a browser for your pleasurable viewing."
  ([html]
    (open-in-browser html "output.html"))
  ([html filename]
    (spit filename html)
    (doto (java.awt.Desktop/getDesktop)
      (.browse (java.net.URI. (str "file://" (pwd) "/" filename))))))
