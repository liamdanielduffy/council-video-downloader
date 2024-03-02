(ns main
  (:require [env :refer [get-env]]
            [clj-http.client :as client]
            [cheshire.core :as json]))

(def api-key (get (get-env) "LEGISTAR_API_KEY"))

(def base-url "https://webapi.legistar.com/v1/nyc/events")

(defn encode [string]
  (java.net.URLEncoder/encode string))

(defn datetime-filter [date-str]
  (str "datetime" \' date-str \'))

(def filter:events-after-2010-11-17
  (encode
   (str "EventDate ge " (datetime-filter "2010-11-17"))))

(defn filter-param [filter-str]
  (str "$filter=" filter-str))

(def token-param
  (str "token=" api-key))

(defn get-events-with-video []
  (let [url (str base-url "?" token-param "&" (filter-param filter:events-after-2010-11-17))
        result (client/get url)
        body-str (get result :body)
        body-json (json/parse-string body-str true)]
    body-json))
  ;; only events after

(count (get-events-with-video))
