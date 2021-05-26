/**

Go语言接受了函数式编程的一些想法，支持匿名函数与闭包。
再如，Go语言接受了以Erlang语言为代表的面向消息编程思想，支持goroutine和通道，并推荐使用消息而不是共享内存来进行并发编程。
总体来说，Go语言是一个非常现代化的语言，精小但非常强大。

Go 语言最主要的特性：
	自动垃圾回收
	更丰富的内置类型
	函数多返回值
	错误处理
	匿名函数和闭包
	类型和接口
	并发编程
	反射
	语言交互性

	文件名与包名没有直接关系，不一定要将文件名与包名定成同一个。
 	文件夹名与包名没有直接关系，并非需要一致。
 	同一个文件夹下的文件只能有一个包名，否则编译报错。
 */
package main

import (
	"fmt"
	"math"
	"strconv"
	"strings"
	"time"
	"unsafe"
)

/**
Go语言变量作用域
作用域为已声明标识符所表示的常量、类型、变量、函数或包在源代码中的作用范围
Go语言变量可以在三个地方声明：
	> 函数内定义变量称为局部变量：它们的作用域只在函数体内，参数和返回值变量也是局部变量
	> 函数外定义变量称为全局变量：全局变量可以在整个包甚至外部包（被导出后）使用
		Go语言程序中全局变量与局部变量名称可以相同，但是函数内的局部变量会被优先考虑
	> 函数定义中的变量称为形式参数

	不同类型的局部和全局变量默认值为：
	---------------------
	|	数据类型	|	0	|
	|	int		|	0	|
	|	float32	|	0	|
	|	pointer	|	nil	|
	---------------------
	默认零值
		- 数值类型（包括complex64/128）为 0
		- 布尔类型 为 false
		- 字符串 为 ""（空字符串）
		- 以下几种类型 为 nil：
			var a *int
			var a []int
			var a map[string] int
			var a chan int
			var a func(string) int
			var a error //error 是接口
*/

/*var声明变量类型；全局变量*/
var m, n = 20, "String"

//这种因式分解关键字的写法一般用于声明全局变量
var (
	/*全局变量允许声明了不使用，但是局部变量不行*/
	j int
	k string
)

/*
	常量中的数据类型只能是布尔型、数字型(整数型、浮点型和复数)和字符串型，隐式类型定义，由编译器根据变量值来推断类型

	常量的定义格式：const identifier [type] = value
*/
const q, w, e, r = 0, "2", false, 'z'
const (
	/*常量作为枚举*/
	Unknow = 0
	Female = 1
	Male   = 2
	Str    = "String"
	/*常量可以用函数计算表达式的值，常量表达式中函数必须是内置函数*/
	ZZZ = len(Str)
	X = unsafe.Sizeof(Str)
	//Y = cap(v)
)

func main() {
	fmt.Println("Hello, World!!!")
	/*下面的省略写法(省略var)必须在方法体内，在方法体外会报错*/
	i, str := 100, "String"
	/*
		unsafe.Sizeof(str)的输出结果是16
		字符串类型在go里是个结构，包含指向底层数组的指针和长度
		这两部分每部分都是8个字节，所以字符串类型大小为16个字节
	*/
	fmt.Println(unsafe.Sizeof(str),len(str))
	fmt.Println(i, str)


	const (
		k = 0
		/*
			iota，特殊常量
			iota在const关键字出现时将被重置为0（const内部的第一行之前），const中每新增一行常量声明将使iota计数一次
			iota可理解为const语句块中的元素索引
		*/
		Test = iota //Test为1，即此const语句块中的第2个元素（从0开始）
		t1          //2
		t2          //3
		t3          //4
		t4   = "ha" //独立值ha，iota += 1
		/*在定义常量组时，如果不提供初始值，则表示将使用上一行的表达式*/
		t5        //ha，iota += 1
		t6 = 100  //t6为100，iota += 1
		t7        //t7也为100，iota += 1
		t8 = iota //t8恢复计数，即t8为9
		t9        //10
		t10
		t11 //t10为11，t11为12
	)

	fmt.Println(k, Test, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11)

	const (
		//iota表示从0开始自动加1，所以 iii = 1 << 0 = 1；jjj = 3 <<< 1 = 6
		iii = 1 << iota
		jjj = 3 << iota
		// kkk = 3 <<< 2 = 12；lll = 3 <<< 3 = 24
		kkk
		lll
	)
	fmt.Println(fmt.Sprintf("iii=%d,jjj=%d,kkk=%d,lll=%d",iii,jjj,kkk,lll))

	/**
	位运算符
	-----------------------------------------------------
	|	p	|	q	|	p & q	|	p | q	|	p ^ q	|
	-----------------------------------------------------
	|	0	|	0	|	  0		|	  0		|	  0		|
	-----------------------------------------------------
	|	0	|	1	|	  0		|	  1		|	  1		|
	-----------------------------------------------------
	|	1	|	1	|	  1		|	  1		|	  0		|
	-----------------------------------------------------
	|	1	|	0	|	  0		|	  1		|	  1		|
	-----------------------------------------------------

	& 返回变量存储地址；&a：将给出变量的实际地址
	* 指针变量；*a：是一个指针变量

	运算符优先级
	二元运算符的运算方向均是从左至右。下表列出了所有运算符以及它们的优先级，由上至下代表优先级由高到低：
	-------------------------------------
	|	优先级	|	     运算符			|
	-------------------------------------
	|	  5		|	* / % << >> & &^	|
	-------------------------------------
	|	  4		|	    + - | ^			|
	-------------------------------------
	|	  3		|	 == != < <= > >=	|
	-------------------------------------
	|	  2		|	       &&			|
	-------------------------------------
	|	  1		|	       ||			|
	-------------------------------------

	 */


	/*go中没有while*/
	/*
		switch语句执行过程从上至下，直到找到匹配项，匹配项后面不需要加break
		switch默认情况下case最后自带break语句，匹配成功后就不会执行其他case，如果需要执行后面的case，可以使用fallthrough
		使用fallthrough会强制执行后面的case语句，fallthrough不会判断下一条case表达式结果是否为true
	*/
	/*
		switch语句还可以被用于 type-switch 来判断某个 interface 变量中实际存储的变量类型
		Type switch语法格式如下：
		switch x.(type){
			case type:
				statement(s);
			case type:
				statement(s);
			......
			default:
				statement(s);
		}
	*/
	var x interface{}
	/*
		不同的 case 之间不使用 break 分隔，默认只会执行一个case
		如果想要执行多个 case，需要使用 fallthrough 关键字，也可用 break 终止
	*/
	switch i := x.(type) {
	case nil:
		fmt.Printf("x 的类型 ：%T", i)
	case int:
		fmt.Printf("x 是 int 型")
	case float64:
		fmt.Printf("x 是 float64 型")
	case func(int) float64:
		fmt.Printf("x 是 func(int) 型")
	case bool, string:
		/*支持多条件匹配*/
		fmt.Printf("x 是 bool 或 string 型")
	default:
		fmt.Printf("未知型")

	}
	/*
		go语言中的switch中，类型不被局限于常量或整数，可以是任何类型，但是必须是同类型的值，且最终结果为相同类型的表达式
	*/

	/*
		select 语句选择一组就绪的send操作和receive操作去处理，类似switch，但是只能用来处理channel操作
		select 是go中的一个控制结构，类似于通信的switch语句。每个case必须是一个通信操作，要么是发送要么是接收
		select 随机执行一个可运行的 case。如果没有case可运行，它将阻塞，直到有case可运行。一个默认的子句应该总是可运行的

		//Go编程语言中的 select 语句的语法如下：
		select {
			select 的 case 可以是 send 语句，也可以是 receive 操作，亦或者 default
			case communication caluse :
				statement(s);
			case communication clause :
				statement(s);
			......
			//最多允许有一个 default case，它可以放在case列表的任何位置，但是一般放最后
			default :
				statement(s);
		}
	*/
	fmt.Println()
	/*
		chan是Go的一个关键字，表示Channel管道，此处为声明3个接收和发送int类型数据的管道
		chan<- float64 表示只可以用来发送 float64 类型的数据
		<-chan int 表示只可以用来接收 int 类型的数据
		<- 总是优先和最左边的类型结合
	*/
	var c1, c2, c3 chan int
	var i1, i2 int
	/*
		select语句的语法
		> 每个 case 都必须是一个通信
		> 所有 channel 表达式都会被求值
		> 所有被发送的表达式都会被求值
		> 如果任意某个通信可以进行，它就执行，其他被忽略
		> 如果有多个 case 都可以运行， select 会随机公平地选出一个执行，其他不会执行
		否则：
			> 如果有 default 子句，则执行该语句
			> 如果没有 default 子句，select 将阻塞，直到某个通信可以运行；Go不会重新对channel或值进行求值
	*/
	select {
	/*i1 接收来自管道c1的值*/
	case i1 = <-c1:
		fmt.Printf("received ", i1, " from c1\n")
	case c2 <- i2:
		fmt.Printf("sent ", i2, " to c2\n")
	case i3, ok := (<-c3):
		/*channel的receive操作可以返回多个值，ok用来检查Channel是否已经被关闭*/
		if ok {
			fmt.Printf("received ", i3, " from c3\n")
		} else {
			fmt.Printf("c3 is closed\n")
		}
		/*
			1.如果向一个已经关闭的Channel写数据则会抛出运行时异常
			2.如果读取一个已经关闭的Channel，则会将缓冲区的数据读取完，而且可以一直读取该Channel数据类型的零值
			3.从一个nil channel中读取数据会一直被block
			4.向一个nil channel写数据会一直被block
		*/
	default:
		fmt.Printf("no communication\n")
	}
	/*Go没有三目运算符，所以不支持 ?: 形式的条件判断*/

	var stockCode = 123
	var endDate = "2021-05-31"
	var url = "Code=%d & endDate=%s"
	fmt.Println("Go 语言中使用 fmt.Sprintf 格式化字符串并赋值给新串："+fmt.Sprintf(url,stockCode,endDate))



	/**
		Go语言的for循环有3中形式，只有其中的一种使用分号
		1.和C语言的for一样
		for init; condition; post {}

		2.和C的while一样
		for condition {}

		3.和C的for(;;)一样
		for {}

		> init: 一般为赋值表达式，给控制变量赋初值
		> condition: 关系表达式或逻辑表达式，循环控制条件
		> post: 一般为赋值表达式，给控制变量增量或减量

		for循环的 range 格式可以对 slice、map、数组、字符串等进行迭代循环。格式如下：
		for key, value := range oldMap {
			//key为oldMap的下标位置
			newMap[key] = value
		}
	*/
	/* 99 乘法表 */
	for m := 1; m < 10; m++ {
		/*Go里没有 ++n 这种操作*/
		for n := 1; n <= m; n++ {
			if n > 1 {
				fmt.Print(" ")
			}
			fmt.Printf("%d x %d = %d", n, m, m*n)
		}
		fmt.Println("")
	}

	/* 求素数 */
	var ii, jj int
	for ii = 2; ii < 100; ii++ {
		for jj = 2; jj <= (ii / jj); jj++ {
			if ii%jj == 0 {
				break //发现因子则不是素数
			}
		}
		if jj > (ii / jj) {
			fmt.Printf("%d 是素数\n", ii)
		}
	}

	/*GOTO语句*/
	loop := 10
	/*GOTO标签*/
LOOP:
	for loop < 20 {
		if loop == 15 {
			loop = loop + 1
			/*跳转到GOTO标签处继续执行*/
			goto LOOP
		}
		fmt.Printf("loop的值为：%d\n", loop)
		loop++
	}

	result := max(10, 20)
	fmt.Printf("max result = %d", result)
	fmt.Println()

	y, x := swap("Google", "Apple")
	fmt.Printf("y = %s, x = %s", y, x)
	fmt.Println()

	swapA, swapB := 10, 20
	/* 值传递 */
	swapResult := swapInt(swapA, swapB)
	fmt.Println("swapA:", swapA, " swapB:", swapB, "swapResult:", swapResult)

	/* 引用传递 */
	swapPtr(&swapA, &swapB)
	fmt.Println("swapA:", swapA, " swapB:", swapB)

	/* 函数作为实参 */
	getSquareRoot := func(x float64) float64 {
		return math.Sqrt(x)
	}
	fmt.Println(getSquareRoot(9))

	/* 此时初始化getSequence() 函数中的变量，即 i 为 0 */
	nextNumber := getSequence()
	/*
		调用 nextNumber 函数，i 变量自增 1 并返回，但是不会初始化函数中的变量
		也就相当于变量赋值后，就已经完成函数内变量初始化，后面调用只会触发返回值的匿名函数
	*/
	fmt.Println(nextNumber()) //1
	fmt.Println(nextNumber()) //2
	fmt.Println(nextNumber()) //3

	/* 再次声明，则当前变量对应的函数内变量为初始值 */
	nextNumber2 := getSequence()
	fmt.Println(nextNumber2()) //1
	fmt.Println(nextNumber2()) //2
	fmt.Println(nextNumber())  //4

	var circle Circle
	circle.radius = 10.00
	/* getArea() 为一个方法 */
	fmt.Println("圆的面积 = ", circle.getArea())

	/**
	Go语言数组
	Go 语言数组声明需要指定元素类型及元素个数，语法格式如下：
	var variable_name [SIZE] variable_type
	初始化数组中 {} 中的元素个数不能大于 [] 中的数字
	如果忽略 [] 中的数字不设置数组大小，Go语言会根据元素的个数来设置数组的大小

	多维数组
	var variable_name [SIZE1][SIZE2]...[SIZEN] variable_type
	*/
	//声明
	var balance [5]float32
	//赋值
	balance = [5]float32{1000.0, 2.0, 3.4, 7.0, 50.0}

	var arr = [10]float32{1.0}
	var arr2 = []int{2,3,4}

	index0 := balance[0]
	fmt.Println("-----")
	fmt.Println(balance, arr, arr2, index0)

	//如果数组长度不确定，可以使用 ... 代替数组的长度，编译器会根据元素个数自行推断数组的长度
	balance = [...]float32{100,2,3,1,5}
	//如果设置了数组长度，可以通过指定下标来初始化元素
	balance = [5]float32{1:20,3:7.0}	//将索引 1 和 3 的元素初始化为 20 和 7.0
	//初始化数组中 {} 中的元素个数不能大于 [] 中的数字
	//如果忽略 [] 中的数字不设置数组大小，Go语言会根据元素的个数来设置数组的大小

	//多维数组
	a := [3][4]int{
		{0, 1, 2, 3},   /*  第一行索引为 0 */
		{4, 5, 6, 7},   /*  第二行索引为 1 */
		{8, 9, 10, 11}, /* 第三行索引为 2 */
	}
	fmt.Println(a)

	/**
	Go语言指针
	变量是一种使用方便的占位符，用于引用计算机内存地址
	Go语言的取地址符是 & ，放到一个变量前面使用就会返回相应变量的内存地址
	一个指针变量指向了一个值的内存地址
	指针声明格式如下：
	var var_name *var_type
	var_type为指针类型，var_name为指针变量名，* 号用于指定变量是作为一个指针
	指针类型前面加上 * 号（前缀）来获取指针所指向的内容

	当一个指针被定义后没有分配到任何变量，它的值为nil
	*/
	var ptrInt *int       /*指向整型*/
	var ptrFloat *float32 /*指向浮点型*/

	intTemp := 10
	ptrInt = &intTemp
	floatTemp := float32(10.00)
	ptrFloat = &floatTemp

	fmt.Printf("ptrInt指针地址：%x，ptrFloat指针地址：%x，ptrInt指针访问值：%d，ptrFloat指针访问值：%f",
		ptrInt, ptrFloat, *ptrInt, *ptrFloat)
	fmt.Println()

	/*声明一个指针数组*/
	var ptrArr [10]*float32
	for i := 0; i < 10; i++ {
		ptrArr[i] = &arr[i]		//浮点型数据地址赋值给指针数组
	}
	for i := 0; i < len(ptrArr); i++ {
		fmt.Printf("当前索引：%d，指针地址：%x，指针访问值：%f",i, ptrArr[i], *ptrArr[i])
		fmt.Println()
	}

	//Go语言指向指针的指针
	var pptr **int
	p := 100
	ptr := &p
	pptr = &ptr
	fmt.Printf("指向指针的指针，指向p的指针地址：%x，指向ptr的指针地址：%x，指向指针的指针的访问值：%x，最终访问值：%d",
		ptr,pptr,*pptr,**pptr)
	fmt.Println()

	/*
		结构体
		数组可以存储同一类型的数据，但在结构体中可以为不同项定义不同的数据类型
		结构体是由一系列具有相同类型或不同类型的数据构成的数据集合

		结构体定义需要使用 type 和 struct 语句。struct 语句定义一个新的数据类型，结构体中有一个或多个成员。
		type 语句设定了结构体的名称
		结构体声明格式如下：
		type struct_variable_type struct {
			member definition;
			member definition;
			...
			member definition;
		}
		一旦定义了结构体类型，它就能用于变量的声明，其语法格式如下：
		variable_name := structure_type {value1,value2,value3...valuen}
		或 使用 key => value 格式
		variable_name := structure_variable_type {key1: value1, key2: value2, ..., keyn: valuen}
		忽略的字段为 0 或 空
		结构体.成员名 = value，这样赋值也行

		访问结构体成员：需要使用点好 . 操作符，格式为：结构体.成员名

	*/
	type Book struct {
		title string
		author string
		subject string
		book_id int
	}

	b := Book{"小狗钱钱","未知","理财",1}
	fmt.Printf("书名：%s，书作者：%s，书主题：%s，书编号：%d",
		b.title,b.author,b.subject,b.book_id)
	fmt.Println()

	//结构体指针
	var struct_ptr *Book
	struct_ptr = &b
	struct_ptr.author = "约瑟夫"
	(*struct_ptr).book_id = 2
	fmt.Printf("书名：%s，书作者：%s，书主题：%s，书编号：%d",
		b.title,b.author,struct_ptr.subject,(*struct_ptr).book_id)
	fmt.Println()

	/*
		Go语言的切片（Slice）
		Go语言切片是对数组的抽象
		Go数组的长度不可改变，在特定场景中这样的集合就不太适用，Go中提供了一种灵活，功能强悍的内置类型切片（"动态数组"），
		与数组相比切片的长度是不固定的，可以追加元素，在追加时可能使切片的容量增大。

		可以声明一个未指定大小的数组来定义切片：var identifier []type
		切片不需要说明长度，或使用 make() 函数来创建切片：var slice1 []type = make([]type, len) 可简写为： slice1 := make([]type,len)
		也可指定容量，其中 capacity 为可选参数：make([]type, len, capacity),这里 len 是数组的长度并且也是切片的初始长度

		s := []int{1,2,3}
		//左闭右开原则
		s1 := s[startIndex:endIndex]
		//只给出了左，右边默认 len(s)
		s2 := s[startIndex:]
		//只给出了右，左边默认 0
		s3 := s[:endIndex]

		可以通过设置下限及上限来设置截取切片 [lower-bound:upper-bound]，
		s[1:4] 表示截取 s 切片从 索引 1 开始到 索引 3（不包含4）（左闭右开） 的数据
	*/
	/* 不指定元素个数的数组初始化其实就是直接初始化切片 */
	s1 := []int{1, 2, 3}
	fmt.Println(s1)
	/* 初始化切片s2，是数组arr2的引用 */
	s2 := arr2[:]
	fmt.Println(s2)
	/* 左闭右开原则，将arr2中从下标 0 到 len(arr2)-1 下的元素创建为一个新的切片 */
	s3 := arr2[0:len(arr2)]
	fmt.Printf("切片s1：%d，切片s2：%d，切片s3：%d ",s1, s2, s3)
	/*
		切片是可索引的，并且可以由 len() 方法获取长度
		切片提供了计算容量的方法 cap() 可以测量切片最长可以达到多少
		一个切片在未初始化之前默认为 nil ，长度为 0，容量也为 0
		可以通过设置下限及上限来设置截取切片 [lower-bound:upper-bound] (左闭右开原则)
	*/
	var numbers []int
	printSlice(numbers)

	/* 允许追加空切片 */
	numbers = append(numbers, 0)
	printSlice(numbers)

	/* 向切片添加一个元素 */
	numbers = append(numbers, 1)
	printSlice(numbers)

	/* 同时添加多个元素 */
	numbers = append(numbers, 2, 3, 4)
	printSlice(numbers)

	/* 创建切片 numbers1 是之前切片的两倍容量  []int 标识为其元素类型为 int 的切片 */
	numbers1 := make([]int, len(numbers), cap(numbers)*2)
	//numbers = make([]int, len(numbers), cap(numbers)*2)
	/* 拷贝 numbers 的内容到 numbers1 */
	copy(numbers1, numbers)
	printSlice(numbers1)

	/*
		Go语言中 range 关键字用于 for循环中迭代数组(array)、切片(slice)、通道(channel)或集合(map)的元素。
		在数组和切片中它返回元素的索引和索引对应的值，在集合中返回key-value对
	*/

	kvs := map[string]string{"a": "apple", "b": "banana"}
	for key, value := range kvs {
		fmt.Println(key, "=>", value)
	}
	/* 如果不需要其中的某个返回值，可以使用空白符 _ 来省略 */
	for _, value := range kvs {
		fmt.Println(value)
	}

	var sumVar int
	for idx,value := range numbers{
		fmt.Printf("数组下标idx：%d，数组下标对应值value：%d ", idx,value)
	}
	//不需要数组的下标时，可以用 _ 接收
	for _,value := range numbers{
		sumVar += value
	}
	fmt.Printf("求和 sumVar：%d", sumVar)
	fmt.Println()

	/*
		Go语言中，可以使用内建函数 make 也可以使用 map 关键字类定义Map
		声明变量，默认 map 是 nil：var map_variable map[key_data_type]value_data_type
		使用 make 函数：map_variable := make(map[key_data_type]value_data_type)
		如果不初始化map，那么就会创建一个nil map，nil map不能用来存放键值对
	*/
	var countryCapitalMap map[string]string
	/* 声明后还未初始化，nil map无法使用，使用make初始化（可以直接声明的时候初始化）*/
	countryCapitalMap = make(map[string]string)
	countryCapitalMap["France"] = "巴黎"
	for key := range countryCapitalMap {
		fmt.Println(key, "首都是：", countryCapitalMap[key])
	}
	for key,value := range countryCapitalMap{
		fmt.Printf("key:%s, value:%s ", key,value)
	}
	/*map会返回此key对应的值是否存在，第二个参数 ok 接收*/
	s, ok := countryCapitalMap["France"]
	if ok {
		fmt.Println("存在首都 ：" + s)
	}
	/*delete()函数用于删除集合的元素，参数为 map 和其对应的 key*/
	fmt.Println(len(countryCapitalMap))
	delete(countryCapitalMap, "France")
	fmt.Println(len(countryCapitalMap))

	/*
		Go语言类型转换基本格式：type_name(expression)   如：float32(20)
							type_name 为类型
							expression 为表达式
		Go不支持隐式转换类型
	*/
	//string 转 int
	str = "123"
	str2Int, _ := strconv.Atoi(str)
	//string 转 int64
	str2Int64, _ := strconv.ParseInt(str, 10, 64)
	//int 转 string
	int2Str := strconv.Itoa(i)
	//int64 转 string
	i64 := int64(i)
	int642Str := strconv.FormatInt(i64,10)

	fmt.Println(fmt.Sprintf("str2Int = %d, str2Int64 = %d, int2Str = %s, int642Str = %s",
		str2Int,str2Int64,int2Str,int642Str))

	/*
	Go语言接口
	Go语言提供了另外一种数据类型 即 接口，它把所有的具有共性的方法定义在一起，任何其他类型只要实现了这些方法就是实现了这个接口

	定义接口
	type interface_name interface {
		method_name1 [return_type]
		method_name2 [return_type]
		method_name3 [return_type]
		...
		method_namen [return_type]
	}
	定义结构体
	type struct_name struct {
		...
	}
	实现接口方法
	func (struct_name_variable struct_name) method_name1() [return_type] {
		//方法实现
	}
	...
	func (struct_name_variable struct_name) method_namen() [return_type] {
		//方法实现
	}
	*/
	iphone := iPhone{"iPhone"}
	nokia := new(Nokia)
	nokia.name = "Nokia"
	iphone.call()
	nokia.call()

	/*
		Go错误处理
		error类型是一个接口类型，它的定义为：
		type error interface {
			Error() string
		}
		我们可以在编码中通过实现error接口类型来生成错误信息
		函数通常在最后的返回值中返回错误信息
		使用 errors.New 可返回一个错误信息
	*/
	/* if中可以进行函数调用赋值，拿到值后才会进行后面的判断 */
	if result, errorMsg := Divide(100, 10); errorMsg == "" {
		fmt.Println("100/10 = ", result)
	}

	if _, errorMsg := Divide(100, 0); errorMsg != "" {
		fmt.Println("errorMsg is : ", errorMsg)
	}

	/*
		Go并发
		Go语言支持并发，只需要通过 go 关键字来开启 goroutine 即可
		goroutine 是轻量级线程，goroutine的调度是由GoLang运行时进行管理的
		goroutine语法格式： go 函数名( 参数列表 )    如： go f(x,y,z)
		Go允许使用 go 语句开启一个新的运行期线程，即 goroutine ，以一个不同的、新创建的 goroutine 来执行一个函数。同一个程序中的所有 goroutine 共享一个地址空间
	*/
	go say("world")
	say("hello")

	/*
		通道(Channel)
		通道(channel)是用来传递数据的一个数据结构
		通道可用于两个 goroutine 之间传递一个指定类型的值来同步运行和通讯。操作符 <- 用于指定通道的方向，发送或接收。如果未指定方向，则为双向通道。
		声明一个通道很简单，使用 chan 关键字即可，通道在使用前必须先创建

		发送操作在接收者准备好之前是阻塞的
	*/
	ch := make(chan int)

	/*
		无法在同一个线程中，对同一个管道即是 接收者，也是发送者
		默认情况下，通道是不带缓冲区的。发送端发送数据，同时必须有接收端相应的接收数据
		v1 := <-ch		//从 ch 接收数据 并把值赋给 v1
		ch <- v			//把 v 发送到通道 ch
	*/

	go out(ch)
	ch <- 5

	ss := []int{7, 2, 8, -9, 4, 0}

	c := make(chan int)

	fmt.Println(len(ss))
	fmt.Println(len(ss) / 2)
	//数组作为入参传递给函数，需要注意，函数的数组形参如果声明了数组长度，则入参的数组也需要指定对应大小的数组长度，如果函数的数组形参没有声明数组长度，那么入参数组声明时也不能指定
	go sum(ss[:len(ss)/2], c)
	go sum(ss[len(ss)/2:], c)

	/*栈的方式进通道和出通道*/
	sumX, sumY := <-c, <-c

	fmt.Println(sumX, sumY, sumX+sumY)

	/*
		通道缓冲区
		通道可以设置缓冲区，通过 make 的第二个参数指定缓冲区大小
		ch := make(chain int, 100)
		带缓冲区的通道允许发送端的数据发送和接收端的数据获取处于异步状态，就是说发送端发送的数据可以放在缓冲区里面，可以等待接收端去获取数据，而不是立刻需要接收端去获取数据
		不过由于缓冲区的大小是有限的，所以还是必须有接收端来接收数据的，否则缓冲区一满，数据发送端就无法再发送数据了
		注意：如果通道不带缓冲，发送方会阻塞直到接收方从通道中接收了值。如果通道带缓冲，发送方则会阻塞直到发送的值被拷贝到缓冲区内；如果缓冲区已满，则意味着需要等待直到某个接收方获取到一个值。接收方在有值可以接收之前会一直阻塞。
	*/
	ch = make(chan int, 2)	//缓冲区大小为2

	//因为 ch 是带缓冲区的通道，所以可以同时发送两个数据，而不用立刻需要去同步读取数据
	ch <- 1
	ch <- 2
	//获取数据
	fmt.Printf("获取缓冲区数据,第一个：%d，第二个：%d ", <-ch, <-ch)
	fmt.Println()


	/*
		Go遍历通道与关闭通道
		Go通过 range 关键字来实现遍历读取到的数据，类似于数组或切片
		for range遍历通道的时候，要确保通道是一个关闭的通道，否则这个遍历不会结束

		//如果通道接收不到数据后 ok 就为 false，这时通道就可以使用 close() 函数来关闭
		newV, ok := <- c
		if ok {
			fmt.Println(newV)
		} else {
			close(c)
		}
	*/
	c = make(chan int, 10)
	/*
		range 函数遍历每个从通道接收到的数据，因为 c 在发送完 10 个
		数据之后就关闭了通道，所以这里我们 range 函数在接收到 10 个数据
		之后就结束了。如果上面的 c 通道不关闭，那么 range 函数就不
		会结束，从而在接收第 11 个数据的时候就阻塞了。
	*/
	go fibonacci(cap(c), c)
	for i := range c {
		fmt.Printf("fib: %d ",i)
	}
	fmt.Println()


	f()
	fmt.Println(f())
	fmt.Println(f1())
	fmt.Println(f2())

	sql := "phone" + ","
	split := strings.Split(sql, ",")
	fmt.Println(split)

	sql += "1" + ","
	sql += "2" + ","

	i3 := strings.Split(sql, ",")[:]
	fmt.Println(i3)


	//函数作为参数传递，实现回调
	testCallBack(1,callBack)
	testCallBack(2, func(i int) int {
		fmt.Printf("我是匿名回调函数，x：%d \n",i)
		return i
	})

	var avgArr = [5]int{1, 2, 3, 4}
	getAverage(avgArr,len(avgArr))
}

func fibonacci(n int, c chan int) {
	x, y := 0, 1
	for i := 0; i < n; i++ {
		c <- x
		x, y = y, x+y
	}
	close(c)
}

func getAverage(arr [5]int, size int) float32 {
	var i, sum int
	var avg float32
	for i = 0; i < size; i++ {
		sum += arr[i]
	}
	avg = float32(sum) / float32(size)
	return avg
}

func sum(s []int, c chan int) {
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum
}

func out(out chan int) {
	fmt.Println(<-out)
}

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(s)
	}
}

/*
	Go语言提供了另外一种数据类型即接口，它把所有的具有共性的方法定义在一起，任何其他类型只要实现了这些方法就是实现了这个接口
	//定义接口：
	type interface_name interface {
		method_name1 [return_type]
		method_name2 [return_type]
		method_name3 [return_type]
		...
		method_namen [return_type]
	}
	//定义结构体
	type struct_name struct {
		// variables
	}
	//实现接口方法
	func (struct_name_variable struct_name) method_name1() [return_type] {
		//方法实现
	}
	...
	func (struct_name_variable struct_name) method_name2() [return_type] {
		//方法实现
	}
	结构体也可以有方法调用
*/
/*声明一个接口，接口里面一个方法*/
type Phone interface {
	//无返回值方法
	call()
}

type Nokia struct {
	name string `json:",omitempty"` //如果这个字段是空值，则不编码到JSON里面，否则用name为名字编码
	//name string `json:"alise,omitempty"`	//如果这个字段是空值，则不编码到JSON里面，否则用alise为名字编码
}

type iPhone struct {
	name string
}

/*在Nokia结构体里实现上述接口的方法*/
func (nokia Nokia) call() {
	fmt.Println(nokia.name)
}

/*在iPhone结构体里实现上述接口的方法*/
func (iphone iPhone) call() {
	fmt.Println(iphone.name)
}

func printSlice(slice []int) {
	/*
		切片是可索引的，并且可以由 len() 方法获取长度
		切片提供了计算容量的方法 cap() 可以测量切片最长可以达到多少
	*/
	if slice == nil {
		//一个切片在未初始化之前默认为 nil，长度为 0
		fmt.Println("切片是空的")
	}
	fmt.Printf("len=%d cap=%d slice=%v\n", len(slice), cap(slice), slice)
}

/*
	Go语言最少有个main()函数
	最少有个main包
	Go函数定义格式如下：
	func function_name( [parameter list] ) [return_types] {
		函数体
	}
	函数定义解析：
		- func：函数由 func 开始声明
		- function_name：函数名称，函数名和参数列表一起构成了函数签名。
		- parameter list：参数列表，参数就像一个占位符，当函数被调用时，你可以将值传递给参数，这个值被称为实际参数。参数列表指定的是参数类型、顺序、及参数个数。参数是可选的，也就是说函数也可以不包含参数。
		- return_types：返回类型，函数返回一列值。return_types 是该列值的数据类型。有些功能不需要返回值，这种情况下 return_types 不是必须的。
		- 函数体：函数定义的代码集合。

*/
func max(num1, num2 int) int {
	result := 0
	if num1 > num2 {
		result = num1
	} else {
		result = num2
	}
	return result
}


/*
	Go语言函数作为入参
*/
//声明一个函数类型
type cb func(int) int

func callBack(x int) int {
	fmt.Printf("我是回调，x ：%d \n", x)
	return x;
}

func testCallBack(x int, f cb) {
	f(x)
}

/**
	Go语言函数作为返回参数
 */
func testFuncVar() {
	/* 声明函数变量 */
	getSquareRoot := func(x float64) float64 {
		return math.Sqrt(x)
	}
	/* 使用函数 */
	fmt.Println(getSquareRoot(9))
}

func f() (result int) {
	/*
		defer 关键字
		defer代码块会在函数调用链表中增加一个函数调用。这个函数调用不是普通的函数调用，而是会在函数正常返回，也就是return之前（return 其实是 给返回值赋值+调用ret指令 两个步骤，defer就是在这两个步骤之间）
		添加一个函数调用，
		因此，defer通常用来释放函数内部变量（类似 finally）
		通过defer，我们可以在代码中优雅的【关闭/清理】代码中所使用的变量。defer作为golang清理变量的特性，有其独有且明确的行为，如下三条规则：
		1.当defer被声明时，其参数就会被实时解析（声明语句当时参数是什么值就是什么值）
		2.defer执行顺序为先进后出（当同时定义了多个defer代码块时，golang按照【先定义后执行】的顺序依次调用defer）
		3.defer可以读取有名返回值（defer代码块的作用域仍然在函数之内）
	*/
	defer func() {
		result++
	}()
	return 0
}

func f1() (r int) {
	t := 5
	defer func() {
		t = t + 5
	}()
	return t
}
func f2() (r int) {
	t := 5
	defer func() {
		t = t + 5
	}()
	return t
}

/* 函数返回多个值 */
func swap(x, y string) (string, string) {
	return y, x
}

/* 值传递 */
func swapInt(x, y int) int {
	temp := x
	x = y
	y = temp
	return temp
}

/*
	引用传递
	等同于 func swapPtr(x *int, y *int) {}
*/
func swapPtr(x, y *int) {
	temp := *x
	*x = *y
	*y = temp
}

/*
	Go语言函数闭包
	此函数返回一个返回值为int的函数
*/
func getSequence() func() int {
	i := 0
	//返回函数闭包，函数闭包里的变量在上面已经声明，调用闭包函数不会在此初始化变量，只会执行闭包函数里的逻辑
	return func() int {
		i += 1
		return i
	}
}

/* 定义结构体 */
type Circle struct {
	radius float64
}

/*
	Go语言函数方法
	Go语言中同时有函数和方法
	一个方法就是包含了接受者的函数，接受者可以是命名类型或者结构体类型的一个值或者是一个指针
	所有给定类型的方法属于该类型的方法集
	语法格式如下：
	func (variable_name variable_data_type) function_name() [return_type] {
		函数体
	}
	以下方法相当于给结构体 Circle声明一个 getArea() 方法，方法里面的执行体是 3.14 * c.radius * c.radius
	相当于 Java 中，Circle 对象中的 getArea 方法
*/
//该 method 属于 Circle 类型对象中的方法
func (c Circle) getArea() float64 {
	return 3.14 * c.radius * c.radius
}
//传递指针，改变对象的值
func (c *Circle) changeRadius(radius float64) {
	c.radius = radius
}

/*定义一个DivideError结构*/
type DivideError struct {
	dividee int
	divider int
}

func (de *DivideError) Error() string {
	//反引号
	strFormat := `
	Cannot proceed, the divider is zero.
	dividee: %d
	divider: 0
	`
	return fmt.Sprintf(strFormat, de.dividee)
}

func Divide(varDividee int, varDivider int) (result int, errorMsg string) {
	if varDivider == 0 {
		dData := DivideError{
			dividee: varDividee,
			divider: varDivider,
		}
		errorMsg = dData.Error()
		//返回值那边写了返回值参数名称，所以这边只要写个 return 就会将此域中的同名参数对应返回
		//如果返回值那边只写了返回类型，没有返回参数，则每个 return 都需要带上参数
		return
	} else {
		return varDividee / varDivider, ""
	}
}
