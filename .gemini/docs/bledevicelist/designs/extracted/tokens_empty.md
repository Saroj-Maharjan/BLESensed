# Token Inventory: stitch_empty.html

## Tailwind Config Overrides

_(none found)_

## Global Styles

Inline `<style>` rules that apply globally — these affect every matching element regardless of class list.

```css
.material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
            display: inline-block;
            line-height: 1;
        }
        @keyframes spin-slow {
            from { transform: rotate(0deg); }
            to { transform: rotate(360deg); }
        }
        .animate-spin-custom {
            animation: spin-slow 2s linear infinite;
        }
        .scanner-ripple {
            position: relative;
            overflow: hidden;
        }
        .scanner-ripple::after {
            content: '';
            position: absolute;
            top: 0; left: 0; right: 0; bottom: 0;
            background: linear-gradient(90deg, transparent, rgba(107, 80, 0, 0.05), transparent);
            transform: translateX(-100%);
            animation: ripple 2s infinite;
        }
        @keyframes ripple {
            100% { transform: translateX(100%); }
        }
body {
      min-height: max(884px, 100dvh);
    }
```

## Motion Inventory

Captured animation vocabulary. Bucket each token via the Web-Motion Policy in `.claude/skills/_shared/motion.md`: **KEEP** the 4 non-interaction families (Ambient bg, Loading/Attention loop, Entrance, Value-driven) + honor reduced-motion; **DROP** all touch press (`active:*`, ripple) and pointer/hover (`hover:*`, `group-hover:*`) feedback. Per-element `animate-*` / `transition-*` / `active:` / `hover:` tags are annotated inline in the Elements section below.

### @keyframes (<style> blocks)

- spin-slow, ripple

### Keyframe magnitudes

Animated value ranges (the delta each animation moves through). Pin these in the blueprint's `## Motion` **Magnitude** column — they are the only source for scale/translate/opacity/offset amounts (duration/easing come from the shorthand above; the implementer must not invent magnitudes).

- **spin-slow**: rotate 0deg→360deg
- **ripple**: translateX 100%

### JS animation drivers

- timed step (Value/Entrance)

## Elements

Each class is followed by its deterministic token interpretation when one applies (e.g. `mt-4 → margin-top: 16dp`). Classes with no annotation are layout primitives, state variants, or unrecognised — interpret them yourself.

**Element formats:**
- **Visual elements** (any class converts to a visual token, or has inline style) get a full block with one line per class.
- **Layout-only elements** (only structural classes like `flex`, `items-center`, `justify-between`) get a single compact line — they still appear in order so structural mismatches (Row vs Column, arrangement, alignment) remain visible.
- **Classless text children** (e.g. `<span>Label</span>` inside a button) also appear as a one-liner with their text, so sibling DOM order inside a flex container is preserved — compare it against the Compose content lambda order.

- [1] `<html>` `light`
**[2] `<body>`**

1. `bg-background`
2. `text-on-surface`
3. `font-body`
4. `min-h-screen` → min-height: 100vh/vw
5. `pb-20` → bottom padding: 80dp
6. `selection:bg-primary-fixed-dim`

### <!-- TopAppBar -->

**[3] `<header>`**

1. `flex`
2. `items-center`
3. `justify-between`
4. `px-4` → horizontal padding: 16dp
5. `py-2` → vertical padding: 8dp
6. `w-full` → width: 100%
7. `z-50` (z-index — Compose has no z-index; layering is order-based)
8. `sticky` (positioning: sticky — Compose: Box overlay or BottomBar slot)
9. `top-0` → top: 0dp
10. `bg-surface`
11. `dark:bg-surface-dim`
12. `shadow-sm` → shadow: ~1dp elevation

**[4] `<div>`**

1. `flex`
2. `items-center`
3. `gap-3` → gap: 12dp

**[5] `<span>` — "bluetooth_searching"**

1. `material-symbols-outlined`
2. `text-primary`
3. `text-2xl` → font-size: 24sp

**[6] `<h1>` — "BLE Devices"**

1. `font-headline`
2. `text-headline-lg`
3. `font-bold` → font-weight: 700 (Bold)
4. `text-primary`
5. `dark:text-primary-fixed-dim`
6. `leading-none` → line-height: 1.0× font-size
7. `tracking-tight` → letter-spacing: -0.025em (× font-size for sp)

**[7] `<button>`**

1. `p-2` → padding: 8dp
2. `rounded-full` → corner-radius: CircleShape
3. `hover:bg-surface-container-high` (motion: web-only — DROP — pointer/hover)
4. `dark:hover:bg-surface-container-highest`
5. `transition-colors` (motion: transition hint — Entrance/Value family)
6. `active:scale-95` (motion: interaction — DROP — touch press feedback)
7. `transition-transform` (motion: transition hint — Entrance/Value family)
8. `duration-150` (motion: transition hint — Entrance/Value family)

- [8] `<span>` `material-symbols-outlined text-on-surface-variant` — "settings"
### <!-- Status Banner (Sticky under App Bar) -->

**[9] `<div>`**

1. `sticky` (positioning: sticky — Compose: Box overlay or BottomBar slot)
2. `top-[52px]` → top: 52px
3. `z-40` (z-index — Compose has no z-index; layering is order-based)
4. `bg-surface-container-low/95`
5. `backdrop-blur-md`
6. `px-4` → horizontal padding: 16dp
7. `py-3` → vertical padding: 12dp
8. `flex`
9. `items-center`
10. `justify-between`
11. `border-b` → border-b width: 1dp
12. `border-outline-variant/30`

**[10] `<div>`**

1. `flex`
2. `items-center`
3. `gap-3` → gap: 12dp

- [11] `<div>` `relative flex items-center justify-center`
**[12] `<span>` — "progress_activity"**

1. `material-symbols-outlined`
2. `text-primary`
3. `animate-spin-custom` (motion: looping 'spin-custom' — looping (family: infer from keyframes))
4. `text-xl` → font-size: 20sp

**[13] `<span>` — "Scanning for devices..."**

1. `text-on-surface-variant`
2. `font-semibold` → font-weight: 600 (SemiBold)
3. `text-sm` → font-size: 14sp

**[14] `<button>` — "Stop Scan"**

1. `bg-primary-container`
2. `text-on-primary-fixed`
3. `px-4` → horizontal padding: 16dp
4. `py-1.5` → vertical padding: 6dp
5. `rounded-full` → corner-radius: CircleShape
6. `text-sm` → font-size: 14sp
7. `font-bold` → font-weight: 700 (Bold)
8. `shadow-sm` → shadow: ~1dp elevation
9. `hover:opacity-90` (motion: web-only — DROP — pointer/hover)
10. `active:scale-95` (motion: interaction — DROP — touch press feedback)
11. `transition-all` (motion: transition hint — Entrance/Value family)

### <!-- Main Content Area -->

**[15] `<main>`**

1. `px-4` → horizontal padding: 16dp
2. `py-6` → vertical padding: 24dp
3. `max-w-2xl`
4. `mx-auto` → horizontal margin: auto
5. `space-y-4` → children spaced 16dp vertically

**[16] `<div>`**

1. `flex`
2. `flex-col`
3. `items-center`
4. `justify-center`
5. `py-20` → vertical padding: 80dp
6. `text-center`

**[17] `<span>` — "search_off"**

1. `material-symbols-outlined`
2. `text-6xl` → font-size: 60sp
3. `mb-4` → bottom margin: 16dp
4. `text-outline`
5. `opacity-40` → opacity: 0.4

**[18] `<h2>` — "No devices found"**

1. `text-xl` → font-size: 20sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-on-surface`
4. `mb-1` → bottom margin: 4dp

**[19] `<p>` — "Start scanning to discover BLE devices"**

1. `text-sm` → font-size: 14sp
2. `text-on-surface-variant/60`
- _inline style_: `color: #4E4730`

### <!-- BottomNavBar -->

**[20] `<nav>`**

1. `fixed` (positioning: fixed — Compose: Box overlay or BottomBar slot)
2. `bottom-0` → bottom: 0dp
3. `left-0` → left: 0dp
4. `w-full` → width: 100%
5. `flex`
6. `justify-around`
7. `items-center`
8. `h-16` → height: 64dp
9. `px-4` → horizontal padding: 16dp
10. `pb-2` → bottom padding: 8dp
11. `bg-surface-container`
12. `dark:bg-surface-container-lowest`
13. `border-t` → border-t width: 1dp
14. `border-outline-variant`
15. `dark:border-outline`
16. `shadow-lg` → shadow: ~8dp elevation
17. `z-50` (z-index — Compose has no z-index; layering is order-based)
18. `rounded-t-xl` → corner-radius (t): 12dp

**[21] `<a>`**

1. `flex`
2. `flex-col`
3. `items-center`
4. `justify-center`
5. `bg-secondary-container`
6. `dark:bg-secondary-container`
7. `text-on-secondary-container`
8. `dark:text-on-secondary-container`
9. `rounded-full` → corner-radius: CircleShape
10. `px-5` → horizontal padding: 20dp
11. `py-1` → vertical padding: 4dp
12. `active:scale-90` (motion: interaction — DROP — touch press feedback)
13. `transition-all` (motion: transition hint — Entrance/Value family)
14. `duration-100` (motion: transition hint — Entrance/Value family)

**[22] `<span>` — "radar"**

1. `material-symbols-outlined`
- _inline style_: `font-variation-settings: 'FILL' 1;`

**[23] `<span>` — "Devices"**

1. `font-label`
2. `text-[10px]` → font-size: 10sp
3. `font-bold` → font-weight: 700 (Bold)
4. `uppercase`
5. `tracking-tighter` → letter-spacing: -0.05em (× font-size for sp)

**[24] `<a>`**

1. `flex`
2. `flex-col`
3. `items-center`
4. `justify-center`
5. `text-on-surface-variant`
6. `dark:text-on-surface-variant`
7. `px-5` → horizontal padding: 20dp
8. `py-1` → vertical padding: 4dp
9. `hover:bg-surface-variant` (motion: web-only — DROP — pointer/hover)
10. `dark:hover:bg-surface-variant`
11. `active:scale-90` (motion: interaction — DROP — touch press feedback)
12. `transition-all` (motion: transition hint — Entrance/Value family)
13. `duration-100` (motion: transition hint — Entrance/Value family)

- [25] `<span>` `material-symbols-outlined` — "terminal"
**[26] `<span>` — "Logs"**

1. `font-label`
2. `text-[10px]` → font-size: 10sp
3. `font-bold` → font-weight: 700 (Bold)
4. `uppercase`
5. `tracking-tighter` → letter-spacing: -0.05em (× font-size for sp)

**[27] `<a>`**

1. `flex`
2. `flex-col`
3. `items-center`
4. `justify-center`
5. `text-on-surface-variant`
6. `dark:text-on-surface-variant`
7. `px-5` → horizontal padding: 20dp
8. `py-1` → vertical padding: 4dp
9. `hover:bg-surface-variant` (motion: web-only — DROP — pointer/hover)
10. `dark:hover:bg-surface-variant`
11. `active:scale-90` (motion: interaction — DROP — touch press feedback)
12. `transition-all` (motion: transition hint — Entrance/Value family)
13. `duration-100` (motion: transition hint — Entrance/Value family)

- [28] `<span>` `material-symbols-outlined` — "bookmark"
**[29] `<span>` — "Saved"**

1. `font-label`
2. `text-[10px]` → font-size: 10sp
3. `font-bold` → font-weight: 700 (Bold)
4. `uppercase`
5. `tracking-tighter` → letter-spacing: -0.05em (× font-size for sp)

---
**Total elements**: 29 | **Visual**: 24 | **Layout-only**: 5 | **Total classes**: 177 | **Auto-converted**: 65 (36%)

