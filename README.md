# data-to-html

A Clojure library for converting data structures into html and opening them in a browser for inspection.

## Lein project

  [:dependencies [data-to-html "0.1.2"]]

## Usage

```clojure
(ns stack-machine.core
  (require [data-to-html.core :refer [convert open-in-browser]]))

(def my-data {:temperature 27 :forecast #{:cloudy :rain :thunder}})

(-> program-1
    convert
    open-in-browser)
```

## License

Copyright © 2013 Erik Svedäng

Distributed under the Eclipse Public License, the same as Clojure.
