## DiagonalTransition

Get an original way of transition between your activity on Compose

## Preview

<p align="center">
<img src="preview/preview.gif" width="270"/>
</p>

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

    val direction = Direction.DIRECTION_TOPRIGHT //choose where you want the screen to moove on
    val shapes = Shapes.CIRCLE //choose the pattern you want to draw

    DiagonalTransition(moove = isMooving,
        shape = shapes,
        direction = direction
    )

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedButton(onClick = { isMooving = !isMooving },
            modifier = Modifier.newOffset(
            isMooving,isCurrentContent = true,
            direction = direction))
        {
            Text(text = "Hello from this side")
        }
        ElevatedButton(onClick = { isMooving = !isMooving }
            , modifier = Modifier.newOffset(
                isMooving,isCurrentContent = false,
                direction = direction))
        {
            Text(text = "Hello from the other side")
        }
    }
```
