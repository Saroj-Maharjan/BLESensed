# Token Inventory: stitch_success.html

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

### <!-- Device Card 1: UG-Sensor-A1 -->

**[16] `<article>`**

1. `bg-surface`
2. `rounded-xl` → corner-radius: 12dp
3. `border` → border-width: 1dp
4. `border-outline-variant`
5. `p-4` → padding: 16dp
6. `shadow-sm` → shadow: ~1dp elevation
7. `scanner-ripple`
8. `hover:border-primary/30` (motion: web-only — DROP — pointer/hover)
9. `transition-colors` (motion: transition hint — Entrance/Value family)

**[17] `<div>`**

1. `flex`
2. `items-start`
3. `justify-between`
4. `mb-3` → bottom margin: 12dp

**[18] `<div>`**

1. `flex`
2. `flex-col`
3. `gap-0.5` → gap: 2dp

**[19] `<h2>` — "UG-Sensor-A1"**

1. `text-lg` → font-size: 18sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-on-surface`

**[20] `<code>` — "00:1A:7D:DA:71:11"**

1. `text-xs` → font-size: 12sp
2. `font-mono`
3. `text-on-surface-variant`
4. `opacity-70` → opacity: 0.7

**[21] `<div>`**

1. `flex`
2. `flex-col`
3. `items-end`
4. `gap-2` → gap: 8dp

**[22] `<span>`**

1. `flex`
2. `items-center`
3. `gap-1` → gap: 4dp
4. `bg-secondary-container`
5. `text-on-secondary-container`
6. `px-2` → horizontal padding: 8dp
7. `py-0.5` → vertical padding: 2dp
8. `rounded-full` → corner-radius: CircleShape
9. `text-xs` → font-size: 12sp
10. `font-bold` → font-weight: 700 (Bold)

**[23] `<span>` — "signal_cellular_alt -64 dBm"**

1. `material-symbols-outlined`
2. `text-sm` → font-size: 14sp

**[24] `<span>` — "Connectable"**

1. `inline-flex`
2. `items-center`
3. `px-2` → horizontal padding: 8dp
4. `py-0.5` → vertical padding: 2dp
5. `rounded-md` → corner-radius: 6dp
6. `text-[10px]` → font-size: 10sp
7. `font-bold` → font-weight: 700 (Bold)
8. `uppercase`
9. `tracking-wider` → letter-spacing: 0.05em (× font-size for sp)
10. `bg-green-100` → background: green-100
11. `text-green-800` → color: green-800
12. `border` → border-width: 1dp
13. `border-green-200` → border-color: green-200

**[25] `<div>`**

1. `space-y-3` → children spaced 12dp vertically
2. `pt-3` → top padding: 12dp
3. `border-t` → border-t width: 1dp
4. `border-outline-variant/30`

**[27] `<span>` — "Advertised Services"**

1. `text-[10px]` → font-size: 10sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-outline`
4. `uppercase`
5. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
6. `block`
7. `mb-1` → bottom margin: 4dp

**[28] `<div>`**

1. `flex`
2. `flex-wrap`
3. `gap-2` → gap: 8dp

**[29] `<span>` — "0x180F (Battery)"**

1. `text-xs` → font-size: 12sp
2. `bg-surface-container-high`
3. `text-on-surface-variant`
4. `px-2` → horizontal padding: 8dp
5. `py-1` → vertical padding: 4dp
6. `rounded-md` → corner-radius: 6dp
7. `font-medium` → font-weight: 500 (Medium)
8. `border` → border-width: 1dp
9. `border-outline-variant/50`

**[30] `<span>` — "0x180A (Device Info)"**

1. `text-xs` → font-size: 12sp
2. `bg-surface-container-high`
3. `text-on-surface-variant`
4. `px-2` → horizontal padding: 8dp
5. `py-1` → vertical padding: 4dp
6. `rounded-md` → corner-radius: 6dp
7. `font-medium` → font-weight: 500 (Medium)
8. `border` → border-width: 1dp
9. `border-outline-variant/50`

**[32] `<span>` — "Manufacturer Data"**

1. `text-[10px]` → font-size: 10sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-outline`
4. `uppercase`
5. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
6. `block`
7. `mb-1` → bottom margin: 4dp

**[33] `<div>`**

1. `p-2` → padding: 8dp
2. `bg-surface-container-lowest`
3. `rounded-lg` → corner-radius: 8dp
4. `border` → border-width: 1dp
5. `border-outline-variant/20`

**[34] `<code>`**

1. `text-[11px]` → font-size: 11sp
2. `font-mono`
3. `text-on-surface-variant`
4. `break-all`
5. `leading-relaxed` → line-height: 1.625× font-size

**[35] `<span>` — "0x004C (Apple): 02 15 FD 3A 6A 81 A2 32 4E 7D B2 E1 01 02 03..."**

1. `text-primary`
2. `font-bold` → font-weight: 700 (Bold)

### <!-- Device Card 2: Unknown Device -->

**[36] `<article>`**

1. `bg-surface`
2. `rounded-xl` → corner-radius: 12dp
3. `border` → border-width: 1dp
4. `border-outline-variant`
5. `p-4` → padding: 16dp
6. `shadow-sm` → shadow: ~1dp elevation
7. `hover:border-primary/30` (motion: web-only — DROP — pointer/hover)
8. `transition-colors` (motion: transition hint — Entrance/Value family)

**[37] `<div>`**

1. `flex`
2. `items-start`
3. `justify-between`
4. `mb-3` → bottom margin: 12dp

**[38] `<div>`**

1. `flex`
2. `flex-col`
3. `gap-0.5` → gap: 2dp

**[39] `<h2>` — "Unknown Device"**

1. `text-lg` → font-size: 18sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-on-surface`
4. `italic`
5. `opacity-60` → opacity: 0.6

**[40] `<code>` — "C4:8A:92:1F:B0:88"**

1. `text-xs` → font-size: 12sp
2. `font-mono`
3. `text-on-surface-variant`
4. `opacity-70` → opacity: 0.7

**[41] `<div>`**

1. `flex`
2. `flex-col`
3. `items-end`
4. `gap-2` → gap: 8dp

**[42] `<span>`**

1. `flex`
2. `items-center`
3. `gap-1` → gap: 4dp
4. `bg-surface-variant`
5. `text-on-surface-variant`
6. `px-2` → horizontal padding: 8dp
7. `py-0.5` → vertical padding: 2dp
8. `rounded-full` → corner-radius: CircleShape
9. `text-xs` → font-size: 12sp
10. `font-bold` → font-weight: 700 (Bold)

**[43] `<span>` — "signal_cellular_alt_2_bar -88 dBm"**

1. `material-symbols-outlined`
2. `text-sm` → font-size: 14sp

**[44] `<span>` — "Limited"**

1. `inline-flex`
2. `items-center`
3. `px-2` → horizontal padding: 8dp
4. `py-0.5` → vertical padding: 2dp
5. `rounded-md` → corner-radius: 6dp
6. `text-[10px]` → font-size: 10sp
7. `font-bold` → font-weight: 700 (Bold)
8. `uppercase`
9. `tracking-wider` → letter-spacing: 0.05em (× font-size for sp)
10. `bg-orange-100` → background: orange-100
11. `text-orange-800` → color: orange-800
12. `border` → border-width: 1dp
13. `border-orange-200` → border-color: orange-200

**[45] `<div>`**

1. `space-y-3` → children spaced 12dp vertically
2. `pt-3` → top padding: 12dp
3. `border-t` → border-t width: 1dp
4. `border-outline-variant/30`

**[47] `<span>` — "Advertised Services"**

1. `text-[10px]` → font-size: 10sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-outline`
4. `uppercase`
5. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
6. `block`
7. `mb-1` → bottom margin: 4dp

**[48] `<p>` — "No public services found"**

1. `text-xs` → font-size: 12sp
2. `italic`
3. `text-on-surface-variant/60`

**[50] `<span>` — "Manufacturer Data"**

1. `text-[10px]` → font-size: 10sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-outline`
4. `uppercase`
5. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
6. `block`
7. `mb-1` → bottom margin: 4dp

**[51] `<div>`**

1. `p-2` → padding: 8dp
2. `bg-surface-container-lowest`
3. `rounded-lg` → corner-radius: 8dp
4. `border` → border-width: 1dp
5. `border-outline-variant/20`

**[52] `<code>`**

1. `text-[11px]` → font-size: 11sp
2. `font-mono`
3. `text-on-surface-variant`
4. `break-all`
5. `leading-relaxed` → line-height: 1.625× font-size

**[53] `<span>` — "0x0059 (Nordic): 01 09 20 02 54 32"**

1. `text-primary`
2. `font-bold` → font-weight: 700 (Bold)

### <!-- Device Card 3: Smart Beacon X -->

**[54] `<article>`**

1. `bg-surface`
2. `rounded-xl` → corner-radius: 12dp
3. `border` → border-width: 1dp
4. `border-outline-variant`
5. `p-4` → padding: 16dp
6. `shadow-sm` → shadow: ~1dp elevation
7. `hover:border-primary/30` (motion: web-only — DROP — pointer/hover)
8. `transition-colors` (motion: transition hint — Entrance/Value family)

**[55] `<div>`**

1. `flex`
2. `items-start`
3. `justify-between`
4. `mb-3` → bottom margin: 12dp

**[56] `<div>`**

1. `flex`
2. `flex-col`
3. `gap-0.5` → gap: 2dp

**[57] `<h2>` — "Smart Beacon X"**

1. `text-lg` → font-size: 18sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-on-surface`

**[58] `<code>` — "FA:22:90:33:EE:41"**

1. `text-xs` → font-size: 12sp
2. `font-mono`
3. `text-on-surface-variant`
4. `opacity-70` → opacity: 0.7

**[59] `<div>`**

1. `flex`
2. `flex-col`
3. `items-end`
4. `gap-2` → gap: 8dp

**[60] `<span>`**

1. `flex`
2. `items-center`
3. `gap-1` → gap: 4dp
4. `bg-secondary-container`
5. `text-on-secondary-container`
6. `px-2` → horizontal padding: 8dp
7. `py-0.5` → vertical padding: 2dp
8. `rounded-full` → corner-radius: CircleShape
9. `text-xs` → font-size: 12sp
10. `font-bold` → font-weight: 700 (Bold)

**[61] `<span>` — "signal_cellular_alt -42 dBm"**

1. `material-symbols-outlined`
2. `text-sm` → font-size: 14sp

**[62] `<span>` — "Connectable"**

1. `inline-flex`
2. `items-center`
3. `px-2` → horizontal padding: 8dp
4. `py-0.5` → vertical padding: 2dp
5. `rounded-md` → corner-radius: 6dp
6. `text-[10px]` → font-size: 10sp
7. `font-bold` → font-weight: 700 (Bold)
8. `uppercase`
9. `tracking-wider` → letter-spacing: 0.05em (× font-size for sp)
10. `bg-green-100` → background: green-100
11. `text-green-800` → color: green-800
12. `border` → border-width: 1dp
13. `border-green-200` → border-color: green-200

**[63] `<div>`**

1. `space-y-3` → children spaced 12dp vertically
2. `pt-3` → top padding: 12dp
3. `border-t` → border-t width: 1dp
4. `border-outline-variant/30`

**[65] `<span>` — "Advertised Services"**

1. `text-[10px]` → font-size: 10sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-outline`
4. `uppercase`
5. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
6. `block`
7. `mb-1` → bottom margin: 4dp

**[66] `<div>`**

1. `flex`
2. `flex-wrap`
3. `gap-2` → gap: 8dp

**[67] `<span>` — "0xFEAA (Eddystone)"**

1. `text-xs` → font-size: 12sp
2. `bg-surface-container-high`
3. `text-on-surface-variant`
4. `px-2` → horizontal padding: 8dp
5. `py-1` → vertical padding: 4dp
6. `rounded-md` → corner-radius: 6dp
7. `font-medium` → font-weight: 500 (Medium)
8. `border` → border-width: 1dp
9. `border-outline-variant/50`

**[68] `<span>` — "0x1803 (Link Loss)"**

1. `text-xs` → font-size: 12sp
2. `bg-surface-container-high`
3. `text-on-surface-variant`
4. `px-2` → horizontal padding: 8dp
5. `py-1` → vertical padding: 4dp
6. `rounded-md` → corner-radius: 6dp
7. `font-medium` → font-weight: 500 (Medium)
8. `border` → border-width: 1dp
9. `border-outline-variant/50`

**[70] `<span>` — "Manufacturer Data"**

1. `text-[10px]` → font-size: 10sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-outline`
4. `uppercase`
5. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
6. `block`
7. `mb-1` → bottom margin: 4dp

**[71] `<div>`**

1. `p-2` → padding: 8dp
2. `bg-surface-container-lowest`
3. `rounded-lg` → corner-radius: 8dp
4. `border` → border-width: 1dp
5. `border-outline-variant/20`

**[72] `<code>`**

1. `text-[11px]` → font-size: 11sp
2. `font-mono`
3. `text-on-surface-variant`
4. `break-all`
5. `leading-relaxed` → line-height: 1.625× font-size

**[73] `<span>` — "0x00E0 (Google): AA FE 10 00 03 75 67 7A 65 72 6F 68 61 72 6..."**

1. `text-primary`
2. `font-bold` → font-weight: 700 (Bold)

### <!-- Aesthetic Empty/Searching State Illustration (Small) -->

**[74] `<div>`**

1. `py-10` → vertical padding: 40dp
2. `flex`
3. `flex-col`
4. `items-center`
5. `justify-center`
6. `opacity-40` → opacity: 0.4

**[75] `<span>` — "radar"**

1. `material-symbols-outlined`
2. `text-6xl` → font-size: 60sp
3. `mb-2` → bottom margin: 8dp
4. `text-outline`

**[76] `<p>` — "Listening for radio frequencies..."**

1. `text-sm` → font-size: 14sp
2. `font-medium` → font-weight: 500 (Medium)

### <!-- BottomNavBar -->

**[77] `<nav>`**

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

**[78] `<a>`**

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

**[79] `<span>` — "radar"**

1. `material-symbols-outlined`
- _inline style_: `font-variation-settings: 'FILL' 1;`

**[80] `<span>` — "Devices"**

1. `font-label`
2. `text-[10px]` → font-size: 10sp
3. `font-bold` → font-weight: 700 (Bold)
4. `uppercase`
5. `tracking-tighter` → letter-spacing: -0.05em (× font-size for sp)

**[81] `<a>`**

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

- [82] `<span>` `material-symbols-outlined` — "terminal"
**[83] `<span>` — "Logs"**

1. `font-label`
2. `text-[10px]` → font-size: 10sp
3. `font-bold` → font-weight: 700 (Bold)
4. `uppercase`
5. `tracking-tighter` → letter-spacing: -0.05em (× font-size for sp)

**[84] `<a>`**

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

- [85] `<span>` `material-symbols-outlined` — "bookmark"
**[86] `<span>` — "Saved"**

1. `font-label`
2. `text-[10px]` → font-size: 10sp
3. `font-bold` → font-weight: 700 (Bold)
4. `uppercase`
5. `tracking-tighter` → letter-spacing: -0.05em (× font-size for sp)

---
**Total elements**: 86 | **Visual**: 75 | **Layout-only**: 5 | **Total classes**: 463 | **Auto-converted**: 226 (48%)

