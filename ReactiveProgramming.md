[什麼是Reactive Programming](https://ithelp.ithome.com.tw/articles/10220286)
[DEFINITION OF REACTIVE](https://www.gushiciku.cn/pl/pC60/zh-tw)
# Reative Programming
A Design Pattern 
- 核心是基於事件流、無阻塞、非同步的，使用反應式程式設計不需要編寫底層的併發、並行程式碼。並且由於其宣告式編寫程式碼的方式，使得非同步程式碼易讀且易維護。

例如：`a+b=c`的場景，在傳統程式設計方式下如果a、b發生變化，那麼我們需要重新計算a+b來得到c的新值。  
而反應式程式設計中，我們不需要重新計算，a、b的變化事件會觸發c的值**自動更新**   

這種方式類似於OOD Oberserver PUSH/SUBSCRIBE模式  
由PUSH事件，而我們的程式碼邏輯作為Subscriber基於事件進行處理，並且是**非同步處理**的。  

反應式程式設計中，最基本的處理單元是事件流(事件流是不可變的，對流進行操作只會返回新的流)中的事件。流中的事件包括正常事件(物件代表的資料、資料流結束標識)和異常事件(異常物件，例如Exception)。同時，只有當訂閱者第一次釋出者，釋出者釋出的事件流才會被消費，後續的訂閱者只能從訂閱點開始消費，但是我們可以通過背壓、流控等方式控制消費。


常用的反應式程式設計實現類庫包括：`Reactor、RxJava 2,、Akka Streams、Vert.x以及Ratpack`     
反應式程式設計與Java8提供的Streams有眾多相似之處(尤其是API上)，且提供了相互轉化的API   
但是**Reactive Programming更加強調非同步非阻塞，通過`onComplete`等註冊監聽的方式避免阻塞，同時支援`delay`、`interval`等特性。而`Streams`本質上是對集合的並行處理，並不是非阻塞**

## Reactor (JAVA)

Reactive DateType in JAVA 
- Flux，是Reactor中的一種Publisher，包含[0,N]個元素的非同步序列。通過其提供的操作可以生成、轉換、編排序列。如果不觸發異常事件，Flux是無限的  
- Mono，是Reactor中的一種Publisher，包含0或者1個的非同步序列。可以用於類似於`Runnable`的場景   
- 背壓(backpressure)，由Subscriber宣告的、限定本消費者可處理的流中的元素個數。

### OPERATOR

[METHODS](https://www.gushiciku.cn/pl/g08e/zh-tw)

所有的STREAM都是不可變(IMMUTABLE)的，所以**對STREAM的操作都會返回一個新的STREAM**

建立(資料流模型)
```diff
just，根據引數建立資料流
never，建立一個不會發出任何資料的無限執行的資料流
empty，建立一個不包含任何資料的資料流，不會無限執行。
error，建立一個訂閱後立刻返回異常的資料流
concact，從多個Mono建立Flux
generate，同步、逐一的建立複雜流。過載方法支援生成狀態。在方法內部的lambda中通過呼叫next和complete、error來指定當前迴圈返回的流中的元素(並不是return)。
create，支援同步、非同步、批量的生成流中的元素。
zip，將多個STREAM合併為一個流，流中的元素一一對應
delay，Mono方法，用於指定流中的第一個元素產生的延遲時間
interval，Flux方法，用於指定流中各個元素產生時間的間隔(包括第一個元素產生時間的延遲)，從0開始的Long物件組成的流
justOrEmpty，Mono方法，用於指定當初始化時的值為null時返回空的流
defaultIfEmpty，Mono方法，用於指定當流中元素為空時產生的預設值
range，生成一個範圍的Integer佇列
```

轉化(就是一些標準函式運算元)
```
map，將流中的資料按照邏輯逐個對映為一個新的資料，當是通過zip建立時，有一個元組入參，元組內元素代表zip前的各個流中的元素。
flatMap，將流中的資料按照邏輯逐個對映一個新的流，新的流之間是非同步的。
take，從流中獲取N個元素，有多個擴充套件方法。
zipMap，將當前流和另一個流合併為一個流，兩個流中的元素一一對應。
mergeWith，將當前流和另一個流合併為一個流，兩個流中的元素按照生成順序合併，無對應關係。
join，將當前流和另一個流合併為一個流，流中的元素不是一一對應的關係，而是根據產生時間進行合併。
concactWith，將當前流和另一個流按宣告順序(不是元素的生成時間)連結在一起，保證第一個流消費完後再消費第二流
zipWith，將當前流和另一個流合併為一個新的流，這個流可以通過lambda表示式設定合併邏輯，並且流中元素一一對應
first，對於Mono返回多個流中，第一個產生元素的Mono。對於Flux，返回多個Flux流中第一個產生元素的Flux。
block，Mono和Flux中類似的方法，用於阻塞當前執行緒直到流中生成元素
toIterable，Flux方法，將Flux生成的元素返回一個迭代器
defer，Flux方法，用於從一個Lambda表示式獲取結果來生成Flux，這個Lambda一般是執行緒阻塞的
buffer相關方法，用於將流中的元素按照時間、邏輯規則分組為多個元素集合，並且這些元素集合組成一個元素型別為集合的新流。
window，與buffer類似，但是window返回的流中元素型別還是流，而不是buffer的集合。
filter，顧名思義，返回負責規則的元素組成的新流
reduce，用於將流中的各個元素與初始值(可以設定)逐一累積，最終得到一個Mono。
```

其他
```
doOnXXX，當流發生XXX時間時的回撥方法，可以有多個，類似於監聽。XXX包括Subscribe、Next、Complete、Error等。
onErrorResume，設定流發生異常時返回的釋出者，此方法的lambda是異常物件
onErrorReturn，設定流發生異常時返回的元素，無法捕獲異常
then，返回Mono，跳過整個流的消費
ignoreElements，忽略整個流中的元素
subscribeOn，配合Scheduler使用，訂閱時的執行緒模型。
publisherOn，配合Scheduler使用，釋出時的執行緒模型。
retry，訂閱者retry次數
```

## funcional
在functional programming裏頭雖然還有很多專有名詞，但可以**簡單把 functional定義為輸入相同會造成相同輸出，沒有SIDE AFFECT的產生**
SIDE EFFECT : 在這裡我們可以想成任何會讓**輸出不固定（非預期）的行為**，像是發送 network 請求（請求可能失敗、錯誤、超時）、File IO ... etc ...

## RxJS
- RxJS最初是由 Microsoft 開源
  > The Reactive Extensions (Rx) is a library for composing asynchronous and event-based programs using observable sequences and LINQ-style query operators

## LINQ？
LINQ（Language Intergrated Query）是一套由程式語言定義的查詢  
- 最初是由 Microsoft .NET Framework 在 2007 年推出。

在程式當中時常有需要**查詢、整合、統計、過濾等對資料進行一連串操作的需求**，但同時也會遇到一些問題：
> **資料來源不一，hard-coded 程式碼難以復用**下`SQL`的話在程式中難以除錯（因為都是字串），而且只有資料庫的資料才能被SQL查詢所以MS推出的這套技術，可以透過一連串的operator來操作資料  

For example 
```java
// string collection
IList<string> stringList = new List<string>() { 
    "C# Tutorials",
    "VB.NET Tutorials",
    "Learn C++",
    "MVC Tutorials" ,
    "Java" 
};

// VIA LINQ Query Syntax
var result = stringList.Where(s => s.Contains("Tutorials"));

```
- 而這個概念被推廣後變成了 ReactiveX，除了 JavaScript 之外還有多個語言實作版本，像是 RxJava RxSwift 等等。
- 透過RxJS，我們可以很方便地管理資料流。

## Observable
從前端的角度來想，其實 UI 的操作也有點像資料庫，我們查詢、過濾有興趣的事件（`click`, `onChange` ...），對事件作操作與轉換（`click` -> ajax call），最後反應到 UI 上。

**整個過程並不一定是同步的**，例如我們並不知道使用者何時會按下按鈕，何時會有offline event，所以想要像陣列一樣可以透過各種operator來簡化操作的話，勢必就要一層抽象。

這一連串的操作可以抽象成`Observable`，當有事件發生時,`Observable`會作出對應的操作。像是在`onClick`當中，使用RxJS可以這麼寫：
```typescript
Observable.fromEvent(document, 'click')
  .filter(e => e.target === myButton)
  .switchMap(() => ajax('/api/list').map(/* operation */))
```
- 乍看之下這個行為與程式碼變複雜了，但實際上我們可以將所有的資料來源（不管是事件、請求、單純的陣列等）用同一個介面操作(減少HARD CODE)，大幅簡化了針對特定型別處理的時間。

最經典的案例或許可以從 auto-complete 這個功能思考：
```typescript
/**
  * 現在 RxJS 6 已經全部改用 pipe 的方式來組織 Observable，但概念一樣是相通的。
  */
const input$ = Observable.fromEvent(input, 'input');
input$
  .filter(e => e.target.value.length > 3) // 輸入大於 3 個字才開會繼續
  .debounceTime(300) // debounce 300ms
  .switchMap(e => ajax('/api/search?q=' + e.target.value) // 建立新的 Observable 發送 ajax 請求
    .map(res => res.data) // map 成想要的資料
  )
  .retry(4) // 如果有錯誤會自動 retry 4 次
  .subscribe(list => {  // 實際 render UI
     // render your data
  })
```
- 這個 Observable 透過一連串的 operator 完成了幾件事：
  > 接收 onInput 事件
  > 過濾任何長度小於 3 的字串(`e.target.value.length > 3`)
  > debounce 300 毫秒(`debounceTime(300)`)
  > 轉換成另一個`Observable`發送請求。（在 rxjs 透過 switchMap 等 high order observable 可以幫你將 Observable 打平）
  > 將Response `map` 成想要的資料
  > 如果有錯誤會`retry(4)`次
  > `subscribe` 結果

## redux-observable
由 Netflix 工程師開源的 redux-observable 近幾年受到熱烈迴響，透過 RxJS 的威力來管理 side-effect 是個相當優雅的做法，搭配 RxJS 的 operator 可以讓我們很容易管理這些複雜的資料流。   
