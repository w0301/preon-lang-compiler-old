# Proposal

Preon programming language is pure functional language that aims to support
all major platforms and operating systems - Android, iOS, Web but also Unix and Windows.

To achieve such wide support (without loosing speed, especially on mobile platforms)
language will be compiled to platforms' "native" languages. This means that compiling
to Java, Swift, JavaScript and C/C++ will be supported. Using platform's "native"
language as target of compilation will also make it easy and effective to wire native
code and fucntions with Preon pure functions - most likely with monads and some kind
of native function invocation.

# Current state

- Following primitive types are supported: Bool, Char, Int, Float, String
- Defining functions is supported:
  ```
  greet : String -> String
  greet name = "Hello: " ++ name
  ```
- Defining binary infix operators with specified precedence (1-9) and
  associativity (l, r) is supported:
  ```
  (<> 9l) : Int -> Int -> String
  (<>) num1 num2 = "(" ++ num1 ++ ", " ++ num2 ++ ")"
  ```
- If conditions are supported:
  ```
  foo : Int -> String
  foo num = if num < 10 then "less than 10"
            else if num < 20 then "less than 20"
            else "really big number"
  ```
- Recursive function calls are supported:
  ```
  isPrime : Int -> Int -> Bool
  isPrime num d = if num <= 1 then False
                  else if d == 1 then True
                  else if num % d == 0 then False
                  else isPrime num (d - 1)
  ```
# To be implemented
