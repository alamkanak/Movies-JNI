//
//  MovieController.hpp
//  Highrise
//
//  Created by Jimmy Xu on 12/19/18.
//  Copyright © 2019 Highrise. All rights reserved.
//

#ifndef MOVIES_MOVIE_CONTROLLER_HPP
#define MOVIES_MOVIE_CONTROLLER_HPP

#include <string>
#include <vector>
#include <map>

namespace movies {
    class Actor {
    public:
        std::string name;
        int age;
        std::string imageUrl;
    };

    class Movie {
    public:
        std::string name;
        int lastUpdated;
    };

    class MovieDetail {
    public:
        std::string name;
        float score;
        std::vector<Actor> actors;
        std::string description;
    };

    class MovieController {
    private:
        std::vector<Movie*> _movies;
        std::map<std::string, MovieDetail*> _details;

    public:
        explicit MovieController() {
            // populate data
            for (int i = 0; i < 10; i++) {
                auto movie = new Movie();
                movie->name = "Top Gun " + std::to_string(i);
                movie->lastUpdated = i * 10000;
                _movies.push_back(movie);

                auto movieDetail = new MovieDetail();
                movieDetail->name = movie->name;
                movieDetail->score = rand() % 10;
                movieDetail->description = "As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.";

                auto tomCruise = Actor();
                tomCruise.name = "Tom Cruise";
                tomCruise.age = 50;

                auto valKilmer = Actor();
                valKilmer.name = "Val Kilmer";
                valKilmer.age = 46;
                valKilmer.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg";

                movieDetail->actors.push_back(tomCruise);
                movieDetail->actors.push_back(valKilmer);

                if (i % 2 == 0) {
                    auto timRobbins = Actor();
                    timRobbins.name = "Tim Robbins";
                    timRobbins.age = 55;
                    timRobbins.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg";

                    movieDetail->actors.push_back(timRobbins);
                } else {
                    auto jenniferConnelly = Actor();
                    jenniferConnelly.name = "Jennifer Connelly";
                    jenniferConnelly.age = 39;
                    jenniferConnelly.imageUrl = "https://m.media-amazon.com/images/M/MV5BOTczNTgzODYyMF5BMl5BanBnXkFtZTcwNjk4ODk4Mw@@._V1_UY317_CR12,0,214,317_AL_.jpg";

                    movieDetail->actors.push_back(jenniferConnelly);
                }

                _details[movie->name] = movieDetail;
            }
        }

        std::vector<Movie*> getMovies() {
            return _movies;
        }

        MovieDetail* getMovieDetail(std::string name) {
            for (auto item:_details) {
                if (item.second->name == name) {
                    return item.second;
                }
            }
            return nullptr;
        }
    };
}

#endif //MOVIES_MOVIE_CONTROLLER_HPP
