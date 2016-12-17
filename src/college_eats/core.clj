(ns college-eats.core)

(def database (atom {}))

(defn create-recipe
  [id name ingredients directions cook-time difficulty]
  {:id id :name name :ingredients ingredients 
   :directions directions :cook-time cook-time :difficulty difficulty})
(defn remove-recipe
  [database id]
  (dissoc database id)
  )
(defn db-remove-recipe!
  [id]
  (swap! database remove-recipe id)
  )
(defn add-recipe
  [database recipe]
  (assoc database (:id recipe) recipe))

(defn db-add-recipe!
  [c]
  (swap! database add-recipe c))

(defn get-recipe
  [id]
  (get @database id))
(def generate-recipe-id
  (let
    [previous-id (atom 0)]
    (fn
     []
      (swap! previous-id inc)
      @previous-id)))

(defn create-sample-database
  []
  (db-add-recipe! (create-recipe (generate-recipe-id) "poptart" "a poptart" "put the poptart in the toaster" "two minuts" "0.5/10"))
  (db-add-recipe! (create-recipe (generate-recipe-id) "ramen noodles" "pack of ramen noodles" "boil noodles, add packet" "5 minutes" "2/10"))
  (db-add-recipe! (create-recipe (generate-recipe-id) "mac and cheese" "box of mac and cheese, milk" "boil noodles, add cheese and milk" "two minutes thirty seconds" "2.7/10"))
  )
(defn get-recipes
  []
  @database)

(create-sample-database)
