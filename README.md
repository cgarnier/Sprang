# Sprang #


Generate AngularJs services from Spring controllers.
This app analyse your spring controllers by java reflection to automaticaly generate an angularjs service ready to be used to call your api.


## Usage ##

  * add your maven dependencies to the pom
  * Run the sample class Main with your jars as parameter 

## What it do ##

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
