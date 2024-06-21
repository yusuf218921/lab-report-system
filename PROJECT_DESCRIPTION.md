# Laboratuvar Raporları Sistemi

## Proje Hakkında

Bu proje, laboratuvar raporlarının incelenmesi, eklenmesi, güncellenmesi ve silinmesini sağlayan bir sistemdir. Sistemde iki farklı yetki düzeyi bulunmaktadır: USER ve ADMIN.

- **USER Yetkisi:** Kullanıcılar rapor ekleme, silme, güncelleme ve raporları getirme işlemlerini yapabilir.
- **ADMIN Yetkisi:** Kullanıcılar rapor işlemlerine ek olarak laborant ekleme, silme ve güncelleme işlemlerini de yapabilir.

## Giriş ve Yetkilendirme

Projenin sağladığı rapor ve laborant servislerine erişim sağlamak için sisteme giriş yapılması gerekmektedir ve giriş yapan kullanıcının gerekli yetkilere sahip olması zorunludur. Sisteme kayıt yapılabilmektedir ancak şu an için roller veritabanından eklenmektedir. İleride, sisteme kayıt olan kullanıcıların rollerini ekleme ve güncelleme gibi işlemlerin sistem üzerinden yapılabilmesi sağlanabilir.

## Test Kullanıcıları

Proje çalışmadan önce oluşturulmuş iki adet test kullanıcısı bulunmaktadır:
- USER rolüne sahip kullanıcı: 
  - Kullanıcı Adı: `user`
  - Şifre: `password`
- ADMIN rolüne sahip kullanıcı: 
  - Kullanıcı Adı: `admin`
  - Şifre: `password`

## DTO'lar ve Mimarisi

Projedeki her bir request ve response işlemi için özel olarak DTO'lar yazılmıştır ve işlemler bu DTO'lar üzerinden gerçekleştirilmektedir. Proje H2 veritabanı kullanılarak geliştirilmiştir ve bu sayede test edilebilirlik daha kolay olmuştur. Servislerin testleri unit test olarak yazılmıştır.

Projenin genel yapısı katmanlı mimari üzerine kurulmuştur. Katmanlı mimari kullanmamın sebebi, projeyi daha düzenli ve SOLID prensiplerine uygun bir şekilde yazabilmemi sağlamasıdır. Projemin genel katmanları içten dışarıya doğru `entity`, `repository`, `service` ve `controller` şeklindedir. Ayrıca, diğer tüm katmanların ortak olarak kullanabileceği bir `core` katmanı bulunmaktadır.

### Entity Katmanı

Entity katmanında veritabanı tablolarının doğrudan karşılığı olan sınıflar ve request/response işlemlerini ele alabileceğim DTO'lar bulunmaktadır. Her bir request ve response işlemi için sadece gerekli olan değerleri alabilmek ve gereksiz değerleri almamak veya göndermemek için DTO'lar kullandım. Projeyi ileride frontend ile bağladığımda bu DTO'lar birçok işi kolaylaştıracaktır.

### ModelMapper Kullanımı

DTO'lar ve entity'ler arasında mapleme işlemi yapabilmek için ModelMapper kullandım. Otomatik mapleme yapabilmesi işimi oldukça kolaylaştırdı. Bazı DTO'ların otomatik maplenmesinde hata olduğu için bu işlemleri kendim ele aldım ve ModelMapperConfig sınıfında nasıl maplenmesi gerektiğini belirttim.

### Yetkilendirme

Yetkilendirme sistemini gerçekleştirmek için Spring Security kullandım ve JWT implementasyonunu gerçekleştirdim. Bu sayede rol bazlı bir sistem geliştirebildim. JwtUtil sınıfı ile JSON Web Token için gerekli işlemleri (rolleri çekme, token oluşturma vb.) ele aldım. JwtAuthenticationFilter ile frontend'den gelen response içindeki token'ı çekebildim ve bu şekilde API'deki tüm request'lerin gerekli rollere göre kullanılabilmesini sağladım. SecurityConfig sınıfında hangi URL'lerin hangi rollere ihtiyaç duyduğunu belirttim ve JwtAuthenticationFilter işlemini burada gerçekleştirdim.

### Testler

API testleri için Swagger-UI kullandım ve daha sonra Postman ile daha detaylı test işlemleri gerçekleştirerek projenin çalışmasını kontrol ettim.

### Repository Katmanı

Repository katmanında veritabanı ile ilgili tüm işlemlerimi ele aldım ve özel query'lerimi burada oluşturdum. Service katmanı, API ile veritabanı arasında köprü görevi görmektedir ve çeşitli iş kurallarını burada ele alıyorum.

### Controller Katmanı

Controller kısmı ise API'yi yazdığım yerdir ve bu şekilde projeyi frontend ile bağlayabiliyor ve API URL'lerini burada tanımlıyorum. Core katmanı, projenin genelinde kullanılabilecek özellikleri içerir. Örneğin, security, validation ve exception handler gibi herhangi bir projede kullanılabilecek durumları bu katmanda yazdım ve bu şekilde ileride başka projeler geliştirdiğimde bu katmandan yararlanabilirim.