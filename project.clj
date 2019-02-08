(defproject data-to-html "0.1.3-SNAPSHOT"
  :description "Convert data to html"
  :url "https://github.com/eriksvedang/data-to-html"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [hiccup "1.0.5"]
                 [garden "1.3.6"]]
  :codox {:output-dir "docs"
          :src-dir-uri "http://github.com/eriksvedang/data-to-html/tree/master/"
          :src-linenum-anchor-prefix "L"})
