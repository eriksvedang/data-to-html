(ns data-to-html.core
  (:require [data-to-html.css :as css]
            [hiccup.core :as h]
            [hiccup.page :as p])
  (:import [java.awt.Desktop]
           [java.net.URI]
           [java.lang.System])
  (:gen-class))

(def visit) ; pre-define symbol so that it can be used in visit-vector and visit-map-pair

(defn- visit-vector
  "Generate a Hiccup data structure for displaying the element of a Clojure vector.
   Each element gets numbered by using the 'index' parameter."
  [index element]
  (list [:div.index (str index)]
        (visit element)))

(defn- visit-map-pair
  "Generate a Hiccup data structure for displaying a key/value pair of a Clojure map.
   Keywords are displayed as strings to look nicer."
  [[k v]]
  (list [:div.key (if (keyword? k) (name k) (str k))]
        (visit v)))

(defn- visit
  "Generate a Hiccup data structure for some kind of Clojure data structure.
   Dispatches on type and works recursively."
  [node]
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

(defn- working-dir
  "Returns the directory the program is running from."
  []
  (java.lang.System/getProperty "user.dir"))

(defn open-in-browser
  "Saves a html string as a file and opens it with your default web browser."
  ([html]
   (open-in-browser html "output.html"))
  ([html filename]
   (spit filename html)
   (doto (java.awt.Desktop/getDesktop)
     (.browse (java.net.URI. (str "file://" (working-dir) "/" filename))))))

(defn inspect
  "Takes a data structure, displays it as a webpage in a browser and then returns the data structure again."
  [data]
  (-> data
      convert
      open-in-browser)
  data)
