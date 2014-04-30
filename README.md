# Sprang #


Generate AngularJs services from Spring controllers.
This app analyse your spring controllers by java reflection to automaticaly generate an angularjs service ready to be used to call your api.


## Usage ##

  * add your maven dependencies to the pom
  * Run the sample class Main with your jars as parameter 

## What it do ##

It search in your jar or source folders for spring controllers like this:

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
  
And genrate an angular module to call it:

 'use strict'

 angular.module('com.twomoro.bfly.ssx.v1.controller.StandardController', [])
 .service('StandardService', ['$http', '$q',
    function ($http, $q) {
      this.delete = function(params){
        var defer = $q.defer();
        $http({
          method: 'GET',
          url: 'http://pc59:8080/MFR/services/standard/delete.json',
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
          url: 'http://pc59:8080/MFR/services/standard/list.json',
          params: params
        }).success(function(data, status){
          defer.resolve(data);
        }).error(function(data,status){
          defer.reject({status: status, data: data});
        });
        return defer.promise;
      };
      this.getbyid = function(params){
        var defer = $q.defer();
        $http({
          method: 'GET',
          url: 'http://pc59:8080/MFR/services/standard/getbyid.json',
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
          url: 'http://pc59:8080/MFR/services/standard/saveorupdate.json',
          params: params
        }).success(function(data, status){
          defer.resolve(data);
        }).error(function(data,status){
          defer.reject({status: status, data: data});
        });
        return defer.promise;
      };
    }]);

