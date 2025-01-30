# EasyDto
EasyDto is java based library for working with DTOs (data transfer objects) of domain objects without requiring to
write any Dto class or mapping logic explicitly

Currently, the library is not available at maven central. Clone the repository and build locally to install in local .m2/

## ⚠️ Up-to-date fork of the original repository 

Original repository: [EasyDto](https://github.com/rahul-acr/easy-dto-core)

- [x] Multi- Superclass support
- [x] Support for the java util List<?> and Enums

---

## Declare DTO classes by simple annotations

```java
public class Student {

    @DtoProperty
    public String name;

    @DtoProperty(value = "dept")
    public Department department;
}
```

Also, the field name in DTO can be customized in the annotation, otherwise the field name is used.

## Derived Properties

Properties can also be derived for DTO. In this case annotate a `getter` with `@DtoProperty` annotation.

```java
@DtoProperty("isEnrolled")
public boolean hasDepartment(){
    return department != null;
}
```

## Support of Profiles

Often we need different types of a single domain object depending on requirement. That can addressed here using
`profile`. Simply declare in the annotation in which profile the field should be present in the DTO. No profile
declaration means all profiles. All properties are picked up when no profile is used during conversion.

```java
public class Student {
    @DtoProperty(profile = {"WEB"})
    public String name;
}
```

## Creating a DTO object

For a domain object of say `Student` class, a DTO can simply be created as following and the mapping is done for
you under the hood.

```java
Student student = new Student("John", new Department(1, "CST"));
Dto<Student> dto = Dto.from(student);
```

Of course, the target profile can be provided and even a custom `DtoConverter` instance can be passed. Otherwise the
default is used.

```java
Dto<Student> dto = Dto.from(student, "REST");
```

## Creating Domain Object from DTO

Simply pass an instance of the target domain object to the DTO to map. This is type-safe.

```java
Dto<Student> dto = deserialize();// some deserialzation logic
Student student = new Student();
dto.map(student);
```

Similary profile and custom `DtoDeConverter` can be provided.

## Serialization

One can not work with DTOs without some sort of serialization. Currently, there is library support for jackson library
only i.e., DTOs can be serialized/deserialized to json. Support for other formats can be extended.

### Working with Jackson

Create an `ObjectMapper` instance and register the out-of-the-box serialization module.

```java
ObjectMapper mapper = new ObjectMapper();
Registerer.registerModules(mapper);
```

### Working with Spring

As long as you are using Jackson for serialization or write your own support for your format, you can use it seamlessly
with Spring.

```java
@Autowired
private ObjectMapper mapper;

// ....
Registerer.registerModules(mapper);
```

Simply `autowire` the object mapper, the one spring has created and will use internally, and register the modules. And
you can run your spring application *without writing the DTO class or any mapping logic of any sort*. Following is a 
sample Spring Rest Endpoint.

```java
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public Dto<Student> get(@PathVariable Long id) {
        Dto<Student> dto = Dto.from(studentService.getStudent(id));
        return dto;
    }

    @PostMapping
    public void post(@RequestBody Dto<Student> dto) {
        Student newStudent = new Student();
        dto.map(newStudent);
        studentService.saveStudent(newStudent);
    }

}
```


## Installation

clone this repository and build locally to install in local .m2. Currently, only maven is supported.

```shell
mvn clean install
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
