(ns college-eats.view
  (:require [hiccup.core :as hc]
            [hiccup.page :as hc-p]
            [college-eats.core :refer :all]
            [ring.util.response :refer  [redirect]]
            [ring.util.anti-forgery :refer (anti-forgery-field)]
            ))

(defn page
  [title body]
  (hc/html
    [:html
     [:center
     [:head
      [:title title]
      [:meta {:charset "utf-8"}]
      (hc-p/include-css "/css/styles.css")

     [:body
      [:ul
       [:li [:a.active {:href "/"} "Home"]]
       [:li [:a {:href "/recipe"} "All Recipies"]]
       [:li  [:a  {:href  "/random"}  "Random"]]
       [:li  [:a  {:href  "/add_recipe"}  "New Recipe"]]
       
       ]
      [:h1 title]
      body]]]]))

(defn recipe->html
  [c]
  (page
    (str (:name c))
    (hc/html
      [:h2 "Ingredients"]
      [:p (:ingredients c)]
      [:h2 "Directions"] 
      [:p (:directions c)]
      [:h2 "Cook-Time"]
      [:p (:cook-time c)]
      [:h2 "Difficulty"]
      [:p (:difficulty c)]
      [:p  [:a.button  {:href (str "/edit_recipe/" (:id c))}  [:span  "Edit Recipe"]]]
      [:p [:a.button {:href (str "/delete_recipe/" (:id c))} [:span "Delete Recipe"]]]
      )))

(defn recipe-list->html
  [recipe-map]
  (page
    "Recipe List"
    (hc/html
      [:ul
       (for [recipe (vals recipe-map)]
         [:p [:a.button {:href (str "/recipe/" (:id recipe))}
               [:span (:name recipe) ]]])])))

(defn home->html
  []
  (page
    "College Eats"
    (hc/html
       [:p [:a.button {:href "/recipe"} [:span "All Recipes"] ]]
       [:p [:a.button {:href "/add_recipe"} [:span "New Recipe!"]]]
       [:p [:a.button {:href "/random"}  [ :span "Random"]]]
       )))

(defn random-recipe->html
  [recipe-map]
    (redirect (str "/recipe/" (:id (rand-nth (vals recipe-map))))))

(defn recipe-form
  [recipe-or-map-with-id]
  (page
    "Edit Recipe"
    (hc/html
      [:form {:action "/commit_edit_recipe" :method "POST"}
       [:input {:type "hidden" :name "id" :value (:id recipe-or-map-with-id)}]
       [:input {:type "text" :name "name" :placeholder "Enter Name" :value (:name recipe-or-map-with-id):size  "90"}]
       [:br]
       [:input {:type "text" :name "ingredients" :placeholder "Enter Ingredients" :value (:ingredients recipe-or-map-with-id):size  "90"}]
       [:br]
       [:input  {:type  "text" :name  "directions" :placeholder  "Enter Directions" :value  (:directions recipe-or-map-with-id) :size "90"}]
       [:br]
       [:input  {:type  "text" :name  "cook-time" :placeholder  "Enter Cook Time" :value  (:cook-time recipe-or-map-with-id):size  "90"}] 
       [:br]
       [:input  {:type  "text" :name  "difficulty" :placeholder  "Enter Difficulty" :value  (:difficulty recipe-or-map-with-id):size  "90"}] 
       (anti-forgery-field)
       [:br]
       [:input {:type "submit" :name "action" :value "Save"}]
       [:input  {:type  "submit" :name  "action" :value  "Cancel"}] 

       ]
      )
    )
  )
























