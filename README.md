# Sprang #


Generate AngularJs services from Spring controllers.
This app analyse your spring controllers by java reflection to automaticaly generate an angularjs service ready to be used to call your api.


## Usage ##

You can use it as a library or use the sample app to generate the javascript source code.


### Sample app ###

``` bash
git clone https://github.com/cgarnier/Sprang.git
cd Sprang
nvm package
```
Modify the conf.json file to configure the app. Customize the templates in the template folder if you dont like it. And run the app.
``` bash
java -jar target/Sprang-....jar
```

## What does it do? ##

It search in your jar or source folders for spring controllers like this:
``` java
  @Controller
  @RequestMapping( "/standard/**" )
  public class StandardController {

    
    @RequestMapping( "/list" )
    public void getAll( HttpServletRequest request, HttpServletResponse response ) {
        
    }

    @RequestMapping( "/getbyid" )
    public void getById( HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value = "id", required = true ) final Long id ) {
        
    }

    @RequestMapping( "/saveorupdate" )
    public void saveOrUpdate( HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value = "id", required = false ) final Long id,
            @RequestParam( value = "name", required = true ) final String name,
            @RequestParam( value = "programId", required = true ) final Long programId ) {
       
    }

    
    @RequestMapping( "/delete" )
    public void delete( HttpServletRequest request, HttpServletResponse response, @RequestParam( "id" ) final Long id )  {
    }

  }
```

And genrate an angular module to call it:

```javascript

'use strict'

angular.module('com.twomoro.bfly.ssx.v1.controller.StandardController', ['angular-sprang'])
    .service('StandardService', ['$http', '$q', 'sprangConfig',
        function ($http, $q, sprangConfig) {
            this.getbyid = function(params){
                var defer = $q.defer();
                $http({
                    method: 'GET',
                    url: sprangConfig.get('bflyApi').url + '/standard/getbyid.json',
                    params: params
                }).success(function(data, status){
                    defer.resolve(data);
                }).error(function(data,status){
                    defer.reject({status: status, data: data});
                });
                return defer.promise;
            };
            this.list = function(params){
                var defer = $q.defer();
                $http({
                    method: 'GET',
                    url: sprangConfig.get('bflyApi').url + '/standard/list.json',
                    params: params
                }).success(function(data, status){
                    defer.resolve(data);
                }).error(function(data,status){
                    defer.reject({status: status, data: data});
                });
                return defer.promise;
            };
            this.saveorupdate = function(params){
                var defer = $q.defer();
                $http({
                    method: 'GET',
                    url: sprangConfig.get('bflyApi').url + '/standard/saveorupdate.json',
                    params: params
                }).success(function(data, status){
                    defer.resolve(data);
                }).error(function(data,status){
                    defer.reject({status: status, data: data});
                });
                return defer.promise;
            };
            this.delete = function(params){
                var defer = $q.defer();
                $http({
                    method: 'GET',
                    url: sprangConfig.get('bflyApi').url + '/standard/delete.json',
                    params: params
                }).success(function(data, status){
                    defer.resolve(data);
                }).error(function(data,status){
                    defer.reject({status: status, data: data});
                });
                return defer.promise;
            };
        }
    ]);

```

