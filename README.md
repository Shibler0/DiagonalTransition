## DiagonalTransition

Get an original way of transition between your activity on Compose

## Implementation

Add this to your build gradle files

```gradle
[settings]
repositories {
#...
maven { url = uri("https://jitpack.io") }
}

[libraries]
#...
implementation ("com.github.Shibler0:DiagonalTransition:0.9")
```

## Example

```kotlin
var isMooving by remember {
        mutableStateOf(false)
    }

    GraphicTransition(isMooving)//use this as a background

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedButton(onClick = { isMooving = !isMooving },
 modifier = Modifier
            .newOffset(isMooving,isCurrentContent = true)//use newOffset as a modifier and set it to true on your components that needs to be attach to the coordinate
) {
            Text(text = "Hello this side")
        }
        ElevatedButton(onClick = { isMooving = !isMooving },
 modifier = Modifier
            .newOffset(isMooving,isCurrentContent = false) //use newOffset as a modifier and set it to false on your components that needs to be attach to the coordinate outside of the screen
) {
            Text(text = "Hello from the other side")
        }
    }
```
